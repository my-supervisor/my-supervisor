/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables;


import com.supervisor.generated.jooq.Keys;
import com.supervisor.generated.jooq.Public;
import com.supervisor.generated.jooq.tables.records.ListDataFieldSourceRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row12;
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
public class ListDataFieldSource extends TableImpl<ListDataFieldSourceRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.list_data_field_source</code>
     */
    public static final ListDataFieldSource LIST_DATA_FIELD_SOURCE = new ListDataFieldSource();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ListDataFieldSourceRecord> getRecordType() {
        return ListDataFieldSourceRecord.class;
    }

    /**
     * The column <code>public.list_data_field_source.creation_date</code>.
     */
    public final TableField<ListDataFieldSourceRecord, LocalDateTime> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.list_data_field_source.creator_id</code>.
     */
    public final TableField<ListDataFieldSourceRecord, UUID> CREATOR_ID = createField(DSL.name("creator_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column
     * <code>public.list_data_field_source.last_modification_date</code>.
     */
    public final TableField<ListDataFieldSourceRecord, LocalDateTime> LAST_MODIFICATION_DATE = createField(DSL.name("last_modification_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.list_data_field_source.last_modifier_id</code>.
     */
    public final TableField<ListDataFieldSourceRecord, UUID> LAST_MODIFIER_ID = createField(DSL.name("last_modifier_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.list_data_field_source.owner_id</code>.
     */
    public final TableField<ListDataFieldSourceRecord, UUID> OWNER_ID = createField(DSL.name("owner_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.list_data_field_source.tag</code>.
     */
    public final TableField<ListDataFieldSourceRecord, String> TAG = createField(DSL.name("tag"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.list_data_field_source.id</code>.
     */
    public final TableField<ListDataFieldSourceRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.list_data_field_source.active</code>.
     */
    public final TableField<ListDataFieldSourceRecord, Boolean> ACTIVE = createField(DSL.name("active"), SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.list_data_field_source.model_id</code>.
     */
    public final TableField<ListDataFieldSourceRecord, UUID> MODEL_ID = createField(DSL.name("model_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.list_data_field_source.order_field_id</code>.
     */
    public final TableField<ListDataFieldSourceRecord, UUID> ORDER_FIELD_ID = createField(DSL.name("order_field_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column
     * <code>public.list_data_field_source.field_to_display_id</code>.
     */
    public final TableField<ListDataFieldSourceRecord, UUID> FIELD_TO_DISPLAY_ID = createField(DSL.name("field_to_display_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.list_data_field_source.field_id</code>.
     */
    public final TableField<ListDataFieldSourceRecord, UUID> FIELD_ID = createField(DSL.name("field_id"), SQLDataType.UUID.nullable(false), this, "");

    private ListDataFieldSource(Name alias, Table<ListDataFieldSourceRecord> aliased) {
        this(alias, aliased, null);
    }

    private ListDataFieldSource(Name alias, Table<ListDataFieldSourceRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.list_data_field_source</code> table
     * reference
     */
    public ListDataFieldSource(String alias) {
        this(DSL.name(alias), LIST_DATA_FIELD_SOURCE);
    }

    /**
     * Create an aliased <code>public.list_data_field_source</code> table
     * reference
     */
    public ListDataFieldSource(Name alias) {
        this(alias, LIST_DATA_FIELD_SOURCE);
    }

    /**
     * Create a <code>public.list_data_field_source</code> table reference
     */
    public ListDataFieldSource() {
        this(DSL.name("list_data_field_source"), null);
    }

    public <O extends Record> ListDataFieldSource(Table<O> child, ForeignKey<O, ListDataFieldSourceRecord> key) {
        super(child, key, LIST_DATA_FIELD_SOURCE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<ListDataFieldSourceRecord> getPrimaryKey() {
        return Keys.LIST_DATA_FIELD_SOURCE_PKEY;
    }

    @Override
    public List<ForeignKey<ListDataFieldSourceRecord, ?>> getReferences() {
        return Arrays.asList(Keys.LIST_DATA_FIELD_SOURCE__LIST_DATA_FIELD_SOURCE_MODEL_ID_FKEY, Keys.LIST_DATA_FIELD_SOURCE__LIST_DATA_FIELD_SOURCE_ORDER_FIELD_ID_FKEY, Keys.LIST_DATA_FIELD_SOURCE__LIST_DATA_FIELD_SOURCE_FIELD_TO_DISPLAY_ID_FKEY, Keys.LIST_DATA_FIELD_SOURCE__LIST_DATA_FIELD_SOURCE_FIELD_ID_FKEY);
    }

    private transient DataModel _dataModel;
    private transient DataField _listDataFieldSourceOrderFieldIdFkey;
    private transient DataField _listDataFieldSourceFieldToDisplayIdFkey;
    private transient EditableDataField _editableDataField;

    /**
     * Get the implicit join path to the <code>public.data_model</code> table.
     */
    public DataModel dataModel() {
        if (_dataModel == null)
            _dataModel = new DataModel(this, Keys.LIST_DATA_FIELD_SOURCE__LIST_DATA_FIELD_SOURCE_MODEL_ID_FKEY);

        return _dataModel;
    }

    /**
     * Get the implicit join path to the <code>public.data_field</code> table,
     * via the <code>list_data_field_source_order_field_id_fkey</code> key.
     */
    public DataField listDataFieldSourceOrderFieldIdFkey() {
        if (_listDataFieldSourceOrderFieldIdFkey == null)
            _listDataFieldSourceOrderFieldIdFkey = new DataField(this, Keys.LIST_DATA_FIELD_SOURCE__LIST_DATA_FIELD_SOURCE_ORDER_FIELD_ID_FKEY);

        return _listDataFieldSourceOrderFieldIdFkey;
    }

    /**
     * Get the implicit join path to the <code>public.data_field</code> table,
     * via the <code>list_data_field_source_field_to_display_id_fkey</code> key.
     */
    public DataField listDataFieldSourceFieldToDisplayIdFkey() {
        if (_listDataFieldSourceFieldToDisplayIdFkey == null)
            _listDataFieldSourceFieldToDisplayIdFkey = new DataField(this, Keys.LIST_DATA_FIELD_SOURCE__LIST_DATA_FIELD_SOURCE_FIELD_TO_DISPLAY_ID_FKEY);

        return _listDataFieldSourceFieldToDisplayIdFkey;
    }

    /**
     * Get the implicit join path to the <code>public.editable_data_field</code>
     * table.
     */
    public EditableDataField editableDataField() {
        if (_editableDataField == null)
            _editableDataField = new EditableDataField(this, Keys.LIST_DATA_FIELD_SOURCE__LIST_DATA_FIELD_SOURCE_FIELD_ID_FKEY);

        return _editableDataField;
    }

    @Override
    public ListDataFieldSource as(String alias) {
        return new ListDataFieldSource(DSL.name(alias), this);
    }

    @Override
    public ListDataFieldSource as(Name alias) {
        return new ListDataFieldSource(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ListDataFieldSource rename(String name) {
        return new ListDataFieldSource(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ListDataFieldSource rename(Name name) {
        return new ListDataFieldSource(name, null);
    }

    // -------------------------------------------------------------------------
    // Row12 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row12<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, Boolean, UUID, UUID, UUID, UUID> fieldsRow() {
        return (Row12) super.fieldsRow();
    }
}
