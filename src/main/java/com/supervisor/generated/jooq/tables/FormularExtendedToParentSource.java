/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables;


import com.supervisor.generated.jooq.Keys;
import com.supervisor.generated.jooq.Public;
import com.supervisor.generated.jooq.tables.records.FormularExtendedToParentSourceRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row10;
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
public class FormularExtendedToParentSource extends TableImpl<FormularExtendedToParentSourceRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of
     * <code>public.formular_extended_to_parent_source</code>
     */
    public static final FormularExtendedToParentSource FORMULAR_EXTENDED_TO_PARENT_SOURCE = new FormularExtendedToParentSource();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<FormularExtendedToParentSourceRecord> getRecordType() {
        return FormularExtendedToParentSourceRecord.class;
    }

    /**
     * The column
     * <code>public.formular_extended_to_parent_source.creation_date</code>.
     */
    public final TableField<FormularExtendedToParentSourceRecord, LocalDateTime> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column
     * <code>public.formular_extended_to_parent_source.creator_id</code>.
     */
    public final TableField<FormularExtendedToParentSourceRecord, UUID> CREATOR_ID = createField(DSL.name("creator_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column
     * <code>public.formular_extended_to_parent_source.last_modification_date</code>.
     */
    public final TableField<FormularExtendedToParentSourceRecord, LocalDateTime> LAST_MODIFICATION_DATE = createField(DSL.name("last_modification_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column
     * <code>public.formular_extended_to_parent_source.last_modifier_id</code>.
     */
    public final TableField<FormularExtendedToParentSourceRecord, UUID> LAST_MODIFIER_ID = createField(DSL.name("last_modifier_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column
     * <code>public.formular_extended_to_parent_source.owner_id</code>.
     */
    public final TableField<FormularExtendedToParentSourceRecord, UUID> OWNER_ID = createField(DSL.name("owner_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.formular_extended_to_parent_source.tag</code>.
     */
    public final TableField<FormularExtendedToParentSourceRecord, String> TAG = createField(DSL.name("tag"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.formular_extended_to_parent_source.id</code>.
     */
    public final TableField<FormularExtendedToParentSourceRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column
     * <code>public.formular_extended_to_parent_source.expr_id</code>.
     */
    public final TableField<FormularExtendedToParentSourceRecord, UUID> EXPR_ID = createField(DSL.name("expr_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column
     * <code>public.formular_extended_to_parent_source.list_source_id</code>.
     */
    public final TableField<FormularExtendedToParentSourceRecord, UUID> LIST_SOURCE_ID = createField(DSL.name("list_source_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column
     * <code>public.formular_extended_to_parent_source.field_id</code>.
     */
    public final TableField<FormularExtendedToParentSourceRecord, UUID> FIELD_ID = createField(DSL.name("field_id"), SQLDataType.UUID.nullable(false), this, "");

    private FormularExtendedToParentSource(Name alias, Table<FormularExtendedToParentSourceRecord> aliased) {
        this(alias, aliased, null);
    }

    private FormularExtendedToParentSource(Name alias, Table<FormularExtendedToParentSourceRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.formular_extended_to_parent_source</code>
     * table reference
     */
    public FormularExtendedToParentSource(String alias) {
        this(DSL.name(alias), FORMULAR_EXTENDED_TO_PARENT_SOURCE);
    }

    /**
     * Create an aliased <code>public.formular_extended_to_parent_source</code>
     * table reference
     */
    public FormularExtendedToParentSource(Name alias) {
        this(alias, FORMULAR_EXTENDED_TO_PARENT_SOURCE);
    }

    /**
     * Create a <code>public.formular_extended_to_parent_source</code> table
     * reference
     */
    public FormularExtendedToParentSource() {
        this(DSL.name("formular_extended_to_parent_source"), null);
    }

    public <O extends Record> FormularExtendedToParentSource(Table<O> child, ForeignKey<O, FormularExtendedToParentSourceRecord> key) {
        super(child, key, FORMULAR_EXTENDED_TO_PARENT_SOURCE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<FormularExtendedToParentSourceRecord> getPrimaryKey() {
        return Keys.FORMULAR_EXTENDED_TO_PARENT_SOURCE_PKEY;
    }

    @Override
    public List<ForeignKey<FormularExtendedToParentSourceRecord, ?>> getReferences() {
        return Arrays.asList(Keys.FORMULAR_EXTENDED_TO_PARENT_SOURCE__FORMULAR_EXTENDED_TO_PARENT_SOURCE_EXPR_ID_FKEY, Keys.FORMULAR_EXTENDED_TO_PARENT_SOURCE__FORMULAR_EXTENDED_TO_PARENT_SOURCE_LIST_SOURCE_ID_FK, Keys.FORMULAR_EXTENDED_TO_PARENT_SOURCE__FORMULAR_EXTENDED_TO_PARENT_SOURCE_FIELD_ID_FKEY);
    }

    private transient FormularExpression _formularExpression;
    private transient ListDataFieldSource _listDataFieldSource;
    private transient EditableDataField _editableDataField;

    /**
     * Get the implicit join path to the <code>public.formular_expression</code>
     * table.
     */
    public FormularExpression formularExpression() {
        if (_formularExpression == null)
            _formularExpression = new FormularExpression(this, Keys.FORMULAR_EXTENDED_TO_PARENT_SOURCE__FORMULAR_EXTENDED_TO_PARENT_SOURCE_EXPR_ID_FKEY);

        return _formularExpression;
    }

    /**
     * Get the implicit join path to the
     * <code>public.list_data_field_source</code> table.
     */
    public ListDataFieldSource listDataFieldSource() {
        if (_listDataFieldSource == null)
            _listDataFieldSource = new ListDataFieldSource(this, Keys.FORMULAR_EXTENDED_TO_PARENT_SOURCE__FORMULAR_EXTENDED_TO_PARENT_SOURCE_LIST_SOURCE_ID_FK);

        return _listDataFieldSource;
    }

    /**
     * Get the implicit join path to the <code>public.editable_data_field</code>
     * table.
     */
    public EditableDataField editableDataField() {
        if (_editableDataField == null)
            _editableDataField = new EditableDataField(this, Keys.FORMULAR_EXTENDED_TO_PARENT_SOURCE__FORMULAR_EXTENDED_TO_PARENT_SOURCE_FIELD_ID_FKEY);

        return _editableDataField;
    }

    @Override
    public FormularExtendedToParentSource as(String alias) {
        return new FormularExtendedToParentSource(DSL.name(alias), this);
    }

    @Override
    public FormularExtendedToParentSource as(Name alias) {
        return new FormularExtendedToParentSource(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public FormularExtendedToParentSource rename(String name) {
        return new FormularExtendedToParentSource(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public FormularExtendedToParentSource rename(Name name) {
        return new FormularExtendedToParentSource(name, null);
    }

    // -------------------------------------------------------------------------
    // Row10 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row10<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, UUID, UUID, UUID> fieldsRow() {
        return (Row10) super.fieldsRow();
    }
}
