/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables;


import com.supervisor.generated.jooq.Keys;
import com.supervisor.generated.jooq.Public;
import com.supervisor.generated.jooq.tables.records.FormularExtendedToModelSourceRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row15;
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
public class FormularExtendedToModelSource extends TableImpl<FormularExtendedToModelSourceRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of
     * <code>public.formular_extended_to_model_source</code>
     */
    public static final FormularExtendedToModelSource FORMULAR_EXTENDED_TO_MODEL_SOURCE = new FormularExtendedToModelSource();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<FormularExtendedToModelSourceRecord> getRecordType() {
        return FormularExtendedToModelSourceRecord.class;
    }

    /**
     * The column
     * <code>public.formular_extended_to_model_source.creation_date</code>.
     */
    public final TableField<FormularExtendedToModelSourceRecord, LocalDateTime> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column
     * <code>public.formular_extended_to_model_source.creator_id</code>.
     */
    public final TableField<FormularExtendedToModelSourceRecord, UUID> CREATOR_ID = createField(DSL.name("creator_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column
     * <code>public.formular_extended_to_model_source.last_modification_date</code>.
     */
    public final TableField<FormularExtendedToModelSourceRecord, LocalDateTime> LAST_MODIFICATION_DATE = createField(DSL.name("last_modification_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column
     * <code>public.formular_extended_to_model_source.last_modifier_id</code>.
     */
    public final TableField<FormularExtendedToModelSourceRecord, UUID> LAST_MODIFIER_ID = createField(DSL.name("last_modifier_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column
     * <code>public.formular_extended_to_model_source.owner_id</code>.
     */
    public final TableField<FormularExtendedToModelSourceRecord, UUID> OWNER_ID = createField(DSL.name("owner_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.formular_extended_to_model_source.tag</code>.
     */
    public final TableField<FormularExtendedToModelSourceRecord, String> TAG = createField(DSL.name("tag"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.formular_extended_to_model_source.id</code>.
     */
    public final TableField<FormularExtendedToModelSourceRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column
     * <code>public.formular_extended_to_model_source.reference_id</code>.
     */
    public final TableField<FormularExtendedToModelSourceRecord, UUID> REFERENCE_ID = createField(DSL.name("reference_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column
     * <code>public.formular_extended_to_model_source.model_id</code>.
     */
    public final TableField<FormularExtendedToModelSourceRecord, UUID> MODEL_ID = createField(DSL.name("model_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.formular_extended_to_model_source.expr_id</code>.
     */
    public final TableField<FormularExtendedToModelSourceRecord, UUID> EXPR_ID = createField(DSL.name("expr_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column
     * <code>public.formular_extended_to_model_source.comparator</code>.
     */
    public final TableField<FormularExtendedToModelSourceRecord, String> COMPARATOR = createField(DSL.name("comparator"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column
     * <code>public.formular_extended_to_model_source.interacting</code>.
     */
    public final TableField<FormularExtendedToModelSourceRecord, Boolean> INTERACTING = createField(DSL.name("interacting"), SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.formular_extended_to_model_source.active</code>.
     */
    public final TableField<FormularExtendedToModelSourceRecord, Boolean> ACTIVE = createField(DSL.name("active"), SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column
     * <code>public.formular_extended_to_model_source.model_field_id</code>.
     */
    public final TableField<FormularExtendedToModelSourceRecord, UUID> MODEL_FIELD_ID = createField(DSL.name("model_field_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column
     * <code>public.formular_extended_to_model_source.field_to_extend_id</code>.
     */
    public final TableField<FormularExtendedToModelSourceRecord, UUID> FIELD_TO_EXTEND_ID = createField(DSL.name("field_to_extend_id"), SQLDataType.UUID.nullable(false), this, "");

    private FormularExtendedToModelSource(Name alias, Table<FormularExtendedToModelSourceRecord> aliased) {
        this(alias, aliased, null);
    }

    private FormularExtendedToModelSource(Name alias, Table<FormularExtendedToModelSourceRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.formular_extended_to_model_source</code>
     * table reference
     */
    public FormularExtendedToModelSource(String alias) {
        this(DSL.name(alias), FORMULAR_EXTENDED_TO_MODEL_SOURCE);
    }

    /**
     * Create an aliased <code>public.formular_extended_to_model_source</code>
     * table reference
     */
    public FormularExtendedToModelSource(Name alias) {
        this(alias, FORMULAR_EXTENDED_TO_MODEL_SOURCE);
    }

    /**
     * Create a <code>public.formular_extended_to_model_source</code> table
     * reference
     */
    public FormularExtendedToModelSource() {
        this(DSL.name("formular_extended_to_model_source"), null);
    }

    public <O extends Record> FormularExtendedToModelSource(Table<O> child, ForeignKey<O, FormularExtendedToModelSourceRecord> key) {
        super(child, key, FORMULAR_EXTENDED_TO_MODEL_SOURCE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<FormularExtendedToModelSourceRecord> getPrimaryKey() {
        return Keys.FORMULAR_EXTENDED_TO_MODEL_SOURCE_PKEY;
    }

    @Override
    public List<ForeignKey<FormularExtendedToModelSourceRecord, ?>> getReferences() {
        return Arrays.asList(Keys.FORMULAR_EXTENDED_TO_MODEL_SOURCE__FORMULAR_EXTENDED_TO_MODEL_SOURCE_REFERENCE_ID_FKEY, Keys.FORMULAR_EXTENDED_TO_MODEL_SOURCE__FORMULAR_EXTENDED_TO_MODEL_SOURCE_MODEL_ID_FKEY, Keys.FORMULAR_EXTENDED_TO_MODEL_SOURCE__FORMULAR_EXTENDED_TO_MODEL_SOURCE_EXPR_ID_FKEY, Keys.FORMULAR_EXTENDED_TO_MODEL_SOURCE__FORMULAR_EXTENDED_TO_MODEL_SOURCE_MODEL_FIELD_ID_FKE, Keys.FORMULAR_EXTENDED_TO_MODEL_SOURCE__FORMULAR_EXTENDED_TO_MODEL_SOURCE_FIELD_TO_EXTEND_ID);
    }

    private transient DataField _dataField;
    private transient DataSheetModel _dataSheetModel;
    private transient FormularExpression _formularExpression;
    private transient EditableDataField _formularExtendedToModelSourceModelFieldIdFke;
    private transient EditableDataField _formularExtendedToModelSourceFieldToExtendId;

    /**
     * Get the implicit join path to the <code>public.data_field</code> table.
     */
    public DataField dataField() {
        if (_dataField == null)
            _dataField = new DataField(this, Keys.FORMULAR_EXTENDED_TO_MODEL_SOURCE__FORMULAR_EXTENDED_TO_MODEL_SOURCE_REFERENCE_ID_FKEY);

        return _dataField;
    }

    /**
     * Get the implicit join path to the <code>public.data_sheet_model</code>
     * table.
     */
    public DataSheetModel dataSheetModel() {
        if (_dataSheetModel == null)
            _dataSheetModel = new DataSheetModel(this, Keys.FORMULAR_EXTENDED_TO_MODEL_SOURCE__FORMULAR_EXTENDED_TO_MODEL_SOURCE_MODEL_ID_FKEY);

        return _dataSheetModel;
    }

    /**
     * Get the implicit join path to the <code>public.formular_expression</code>
     * table.
     */
    public FormularExpression formularExpression() {
        if (_formularExpression == null)
            _formularExpression = new FormularExpression(this, Keys.FORMULAR_EXTENDED_TO_MODEL_SOURCE__FORMULAR_EXTENDED_TO_MODEL_SOURCE_EXPR_ID_FKEY);

        return _formularExpression;
    }

    /**
     * Get the implicit join path to the <code>public.editable_data_field</code>
     * table, via the
     * <code>formular_extended_to_model_source_model_field_id_fke</code> key.
     */
    public EditableDataField formularExtendedToModelSourceModelFieldIdFke() {
        if (_formularExtendedToModelSourceModelFieldIdFke == null)
            _formularExtendedToModelSourceModelFieldIdFke = new EditableDataField(this, Keys.FORMULAR_EXTENDED_TO_MODEL_SOURCE__FORMULAR_EXTENDED_TO_MODEL_SOURCE_MODEL_FIELD_ID_FKE);

        return _formularExtendedToModelSourceModelFieldIdFke;
    }

    /**
     * Get the implicit join path to the <code>public.editable_data_field</code>
     * table, via the
     * <code>formular_extended_to_model_source_field_to_extend_id</code> key.
     */
    public EditableDataField formularExtendedToModelSourceFieldToExtendId() {
        if (_formularExtendedToModelSourceFieldToExtendId == null)
            _formularExtendedToModelSourceFieldToExtendId = new EditableDataField(this, Keys.FORMULAR_EXTENDED_TO_MODEL_SOURCE__FORMULAR_EXTENDED_TO_MODEL_SOURCE_FIELD_TO_EXTEND_ID);

        return _formularExtendedToModelSourceFieldToExtendId;
    }

    @Override
    public FormularExtendedToModelSource as(String alias) {
        return new FormularExtendedToModelSource(DSL.name(alias), this);
    }

    @Override
    public FormularExtendedToModelSource as(Name alias) {
        return new FormularExtendedToModelSource(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public FormularExtendedToModelSource rename(String name) {
        return new FormularExtendedToModelSource(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public FormularExtendedToModelSource rename(Name name) {
        return new FormularExtendedToModelSource(name, null);
    }

    // -------------------------------------------------------------------------
    // Row15 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row15<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, UUID, UUID, UUID, String, Boolean, Boolean, UUID, UUID> fieldsRow() {
        return (Row15) super.fieldsRow();
    }
}
