/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables;


import com.supervisor.generated.jooq.Keys;
import com.supervisor.generated.jooq.Public;
import com.supervisor.generated.jooq.tables.records.DataFieldOfSheetRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row13;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DataFieldOfSheet extends TableImpl<DataFieldOfSheetRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.data_field_of_sheet</code>
     */
    public static final DataFieldOfSheet DATA_FIELD_OF_SHEET = new DataFieldOfSheet();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DataFieldOfSheetRecord> getRecordType() {
        return DataFieldOfSheetRecord.class;
    }

    /**
     * The column <code>public.data_field_of_sheet.creation_date</code>.
     */
    public final TableField<DataFieldOfSheetRecord, LocalDateTime> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.data_field_of_sheet.creator_id</code>.
     */
    public final TableField<DataFieldOfSheetRecord, UUID> CREATOR_ID = createField(DSL.name("creator_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column
     * <code>public.data_field_of_sheet.last_modification_date</code>.
     */
    public final TableField<DataFieldOfSheetRecord, LocalDateTime> LAST_MODIFICATION_DATE = createField(DSL.name("last_modification_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.data_field_of_sheet.last_modifier_id</code>.
     */
    public final TableField<DataFieldOfSheetRecord, UUID> LAST_MODIFIER_ID = createField(DSL.name("last_modifier_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.data_field_of_sheet.owner_id</code>.
     */
    public final TableField<DataFieldOfSheetRecord, UUID> OWNER_ID = createField(DSL.name("owner_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.data_field_of_sheet.tag</code>.
     */
    public final TableField<DataFieldOfSheetRecord, String> TAG = createField(DSL.name("tag"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.data_field_of_sheet.id</code>.
     */
    public final TableField<DataFieldOfSheetRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.data_field_of_sheet.origin_field_id</code>.
     */
    public final TableField<DataFieldOfSheetRecord, UUID> ORIGIN_FIELD_ID = createField(DSL.name("origin_field_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.data_field_of_sheet.sheet_id</code>.
     */
    public final TableField<DataFieldOfSheetRecord, UUID> SHEET_ID = createField(DSL.name("sheet_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.data_field_of_sheet.value</code>.
     */
    public final TableField<DataFieldOfSheetRecord, String> VALUE = createField(DSL.name("value"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.data_field_of_sheet.default_value</code>.
     */
    public final TableField<DataFieldOfSheetRecord, String> DEFAULT_VALUE = createField(DSL.name("default_value"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.data_field_of_sheet.structure_id</code>.
     */
    public final TableField<DataFieldOfSheetRecord, UUID> STRUCTURE_ID = createField(DSL.name("structure_id"), SQLDataType.UUID, this, "");

    /**
     * The column <code>public.data_field_of_sheet.sheet_to_refer_id</code>.
     */
    public final TableField<DataFieldOfSheetRecord, UUID> SHEET_TO_REFER_ID = createField(DSL.name("sheet_to_refer_id"), SQLDataType.UUID, this, "");

    private DataFieldOfSheet(Name alias, Table<DataFieldOfSheetRecord> aliased) {
        this(alias, aliased, null);
    }

    private DataFieldOfSheet(Name alias, Table<DataFieldOfSheetRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.data_field_of_sheet</code> table reference
     */
    public DataFieldOfSheet(String alias) {
        this(DSL.name(alias), DATA_FIELD_OF_SHEET);
    }

    /**
     * Create an aliased <code>public.data_field_of_sheet</code> table reference
     */
    public DataFieldOfSheet(Name alias) {
        this(alias, DATA_FIELD_OF_SHEET);
    }

    /**
     * Create a <code>public.data_field_of_sheet</code> table reference
     */
    public DataFieldOfSheet() {
        this(DSL.name("data_field_of_sheet"), null);
    }

    public <O extends Record> DataFieldOfSheet(Table<O> child, ForeignKey<O, DataFieldOfSheetRecord> key) {
        super(child, key, DATA_FIELD_OF_SHEET);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<DataFieldOfSheetRecord> getPrimaryKey() {
        return Keys.DATA_FIELD_OF_SHEET_PKEY;
    }

    @Override
    public List<ForeignKey<DataFieldOfSheetRecord, ?>> getReferences() {
        return Arrays.asList(Keys.DATA_FIELD_OF_SHEET__DATA_FIELD_OF_SHEET_ORIGIN_FIELD_ID_FKEY, Keys.DATA_FIELD_OF_SHEET__DATA_FIELD_OF_SHEET_SHEET_ID_FKEY, Keys.DATA_FIELD_OF_SHEET__DATA_FIELD_OF_SHEET_STRUCTURE_ID_FKEY, Keys.DATA_FIELD_OF_SHEET__DATA_FIELD_OF_SHEET_SHEET_TO_REFER_ID_FKEY);
    }

    private transient EditableDataField _editableDataField;
    private transient DataSheet _dataFieldOfSheetSheetIdFkey;
    private transient DataSheetModel _dataSheetModel;
    private transient DataSheet _dataFieldOfSheetSheetToReferIdFkey;

    /**
     * Get the implicit join path to the <code>public.editable_data_field</code>
     * table.
     */
    public EditableDataField editableDataField() {
        if (_editableDataField == null)
            _editableDataField = new EditableDataField(this, Keys.DATA_FIELD_OF_SHEET__DATA_FIELD_OF_SHEET_ORIGIN_FIELD_ID_FKEY);

        return _editableDataField;
    }

    /**
     * Get the implicit join path to the <code>public.data_sheet</code> table,
     * via the <code>data_field_of_sheet_sheet_id_fkey</code> key.
     */
    public DataSheet dataFieldOfSheetSheetIdFkey() {
        if (_dataFieldOfSheetSheetIdFkey == null)
            _dataFieldOfSheetSheetIdFkey = new DataSheet(this, Keys.DATA_FIELD_OF_SHEET__DATA_FIELD_OF_SHEET_SHEET_ID_FKEY);

        return _dataFieldOfSheetSheetIdFkey;
    }

    /**
     * Get the implicit join path to the <code>public.data_sheet_model</code>
     * table.
     */
    public DataSheetModel dataSheetModel() {
        if (_dataSheetModel == null)
            _dataSheetModel = new DataSheetModel(this, Keys.DATA_FIELD_OF_SHEET__DATA_FIELD_OF_SHEET_STRUCTURE_ID_FKEY);

        return _dataSheetModel;
    }

    /**
     * Get the implicit join path to the <code>public.data_sheet</code> table,
     * via the <code>data_field_of_sheet_sheet_to_refer_id_fkey</code> key.
     */
    public DataSheet dataFieldOfSheetSheetToReferIdFkey() {
        if (_dataFieldOfSheetSheetToReferIdFkey == null)
            _dataFieldOfSheetSheetToReferIdFkey = new DataSheet(this, Keys.DATA_FIELD_OF_SHEET__DATA_FIELD_OF_SHEET_SHEET_TO_REFER_ID_FKEY);

        return _dataFieldOfSheetSheetToReferIdFkey;
    }

    @Override
    public DataFieldOfSheet as(String alias) {
        return new DataFieldOfSheet(DSL.name(alias), this);
    }

    @Override
    public DataFieldOfSheet as(Name alias) {
        return new DataFieldOfSheet(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DataFieldOfSheet rename(String name) {
        return new DataFieldOfSheet(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public DataFieldOfSheet rename(Name name) {
        return new DataFieldOfSheet(name, null);
    }

    // -------------------------------------------------------------------------
    // Row13 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row13<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, UUID, UUID, String, String, UUID, UUID> fieldsRow() {
        return (Row13) super.fieldsRow();
    }
}
