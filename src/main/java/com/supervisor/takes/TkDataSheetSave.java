package com.supervisor.takes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.utils.OptUUID;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.User;
import com.supervisor.domain.UserScope;
import com.supervisor.domain.DataFieldOfSheet;
import com.supervisor.domain.DataFieldStyle;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.DataSheet;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.DataSheetOfModels;
import com.supervisor.domain.DataSheets;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.ListDataFieldOfSheet;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.TableDataField;
import com.supervisor.domain.TableDataFieldOfSheet;
import com.supervisor.domain.TableDataFieldOfSheetRow;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.domain.impl.ListDataFieldUpdaterImpl;
import com.supervisor.domain.impl.PxTableDataFieldOfSheet;

public final class TkDataSheetSave extends TkBaseWrap {

	public TkDataSheetSave(Base base) {
		super(
				base, 
				req -> {
					
					final Supervisor module = new PxSupervisor(base, req);
					final DataSheets myItems = module.dataSheets();
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final DataSheet itemSaved;
					final DataSheetModel model;
					
					final OptUUID id = new OptUUID(new RqHref.Smart(req).single("id", "0"));
					
					final LocalDate date;
					if(StringUtils.isBlank(form.single("DATE", StringUtils.EMPTY))) {
						date = null;
					} else {
						try {
							date = LocalDate.parse(form.single("DATE"), DateTimeFormatter.ISO_LOCAL_DATE);
						} catch (Exception e) {
							throw new IllegalArgumentException("Le format de la date de production n'est pas correct !");
						}
					}
						
					boolean merged = false;		
					if(id.isPresent()) {
						itemSaved = myItems.get(id.get());
						model = itemSaved.model();
						
						if(!itemSaved.model().isTable()) {
							itemSaved.update(date);
						}						
					} else {			
						UUID modelId = UUID.fromString(new RqHref.Smart(req).single("model"));
						model = module.dataSheetModels().get(modelId);
						
						// vérifier qu'il n'existe pas une feuille du même auteur à la même date
						User user = new RqUser(base, req);
						DataSheetOfModels sheets = model.sheets()
											     		.where(DataSheet::creatorId, user.id())
											     		.where(DataSheet::date, date);
												
						if(sheets.any() && model.canMergeAtSameDate()) {				
							itemSaved = sheets.last();
							merged = true;
						} else {
							final String reference = form.single("REF");
							itemSaved = model.sheets().add(reference, date);
						}				
					}
					
					// enregistrer les champs			
					for (String name : form.names()) {	
						
						if(name.equals("new_item") || name.contains("row_") || name.equals("table") || name.startsWith("key-") || (name.equalsIgnoreCase("REF") && merged))
							continue;
						
						String value = form.single(name);
						String reference = form.single(String.format("key-%s", name), StringUtils.EMPTY);
						itemSaved.fields().put(name.toUpperCase(), reference, value);					
					}
					
					// enregistrer les tables
					int nbOfTablesToTreat = getValuesOfRow("table", form).size();
					for (int i = 0; i < nbOfTablesToTreat; i++) {
						String code = getRowValueAt("table", form, i);
						
						if(itemSaved.fields().where(DataFieldOfSheet::code, code.toUpperCase()).isEmpty()) {
							TableDataField field = model.fields().tables().get(code.toUpperCase());
							itemSaved.fields().add(field);
						}	
					}
					
					// enregistrer les rangées des tables
					List<TableDataFieldOfSheet> tables = itemSaved.fields().where(DataFieldOfSheet::type, DataFieldType.TABLE)
							                                               .items()
							                                               .stream()
							                                               .map(c -> {
																			try {
																				return new PxTableDataFieldOfSheet(c);
																			} catch (IOException e) {
																				e.printStackTrace();
																				throw new RuntimeException(e);
																			}
																		})
							                                               .collect(Collectors.toList());
					
					for (TableDataFieldOfSheet table : tables) {
						
						String prefix = String.format("row_%s_", table.code());
						
						int nbOfRowToTreat = getValuesOfRow(prefix + "table", form).size();		
						List<EditableDataField> columns = table.columns();
						
						for (int i = 0; i < nbOfRowToTreat; i++) {
							String state = getRowValueAt(prefix + "state", form, i);
							
							if(state.equals("added")) {					
								final TableDataFieldOfSheetRow row = table.rows().add();					
								for (EditableDataField column : columns) {
									if(column.userScope() == UserScope.SYSTEM)
										continue;
									
									final String value = getRowValueAt(prefix + column.code().toUpperCase(), form, i); 
									
									if(column.style() == DataFieldStyle.LIST) {
										final String reference = getRowValueAt("key-" + prefix + column.code().toUpperCase(), form, i);
										row.fields().put(column.code(), reference, value);
									} else {
										row.fields().put(column.code(), StringUtils.EMPTY, value);
									}
								}	
								
								row.validate();
							} else if(state.equals("removed")) {
								UUID rowId = UUID.fromString(getRowValueAt(prefix + "id", form, i));
								table.rows().remove(rowId);
							} else { // modified
								UUID rowId = UUID.fromString(getRowValueAt(prefix + "id", form, i));
								TableDataFieldOfSheetRow row = table.rows().get(rowId);
								for (DataFieldOfSheet cell : row.fields().items()) {
									if(cell.userScope() == UserScope.SYSTEM)
										continue;
									
									final String value = getRowValueAt(prefix + cell.code().toUpperCase(), form, i); 
									if(cell.style() == DataFieldStyle.LIST) {
										final String reference = getRowValueAt("key-" + prefix + cell.code().toUpperCase(), form, i);
										((ListDataFieldOfSheet)cell).update(reference, value);
									} else {
										cell.update(value);
									}
								}	
								
								row.validate();
							}
						}
					}		
					
					final List<DataSheet> sheetsUpdated = new ListDataFieldUpdaterImpl(itemSaved).update();
					sheetsUpdated.add(itemSaved);
					
					for (DataSheet sheet : sheetsUpdated) {
						module.activityNotification().publish(sheet.model());
					}					
					
					final String msg;
					
					if(merged) {
						msg = String.format("Les données de la feuille %s ont été fusionnées avec succès !", itemSaved.reference());
					} else {
						if(id.isPresent())
							msg = String.format("La feuille %s a été modifiée avec succès !", itemSaved.reference());
						else
							msg = String.format("La feuille %s a été créée avec succès !", itemSaved.reference());
					}
					
					return new RsForward(
							new RsFlash(
				                msg,
				                Level.FINE
				            ),
				            "/collect/sheet/edit?id=" + itemSaved.id()
					);		
				}
		);
	}

	@SuppressWarnings("unchecked")
	private static List<String> getValuesOfRow(final String name, RqFormSmart form) throws IOException {
		return IteratorUtils.toList(form.param(name).iterator());
	}
	
	private static String getRowValueAt(final String name, RqFormSmart form, int index) throws IOException {
		return getValuesOfRow(name, form).get(index);
	}
	
	private static Long getRowLongValueAt(final String name, RqFormSmart form, int index) throws IOException {
		return Long.parseLong(getRowValueAt(name, form, index));
	}
}
