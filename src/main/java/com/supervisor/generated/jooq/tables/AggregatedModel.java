/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables;


import com.supervisor.generated.jooq.Keys;
import com.supervisor.generated.jooq.Public;
import com.supervisor.generated.jooq.tables.records.AggregatedModelRecord;

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
public class AggregatedModel extends TableImpl<AggregatedModelRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.aggregated_model</code>
     */
    public static final AggregatedModel AGGREGATED_MODEL = new AggregatedModel();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AggregatedModelRecord> getRecordType() {
        return AggregatedModelRecord.class;
    }

    /**
     * The column <code>public.aggregated_model.creation_date</code>.
     */
    public final TableField<AggregatedModelRecord, LocalDateTime> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.aggregated_model.creator_id</code>.
     */
    public final TableField<AggregatedModelRecord, UUID> CREATOR_ID = createField(DSL.name("creator_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.aggregated_model.last_modification_date</code>.
     */
    public final TableField<AggregatedModelRecord, LocalDateTime> LAST_MODIFICATION_DATE = createField(DSL.name("last_modification_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.aggregated_model.last_modifier_id</code>.
     */
    public final TableField<AggregatedModelRecord, UUID> LAST_MODIFIER_ID = createField(DSL.name("last_modifier_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.aggregated_model.owner_id</code>.
     */
    public final TableField<AggregatedModelRecord, UUID> OWNER_ID = createField(DSL.name("owner_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.aggregated_model.tag</code>.
     */
    public final TableField<AggregatedModelRecord, String> TAG = createField(DSL.name("tag"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.aggregated_model.id</code>.
     */
    public final TableField<AggregatedModelRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.aggregated_model.core_model_id</code>.
     */
    public final TableField<AggregatedModelRecord, UUID> CORE_MODEL_ID = createField(DSL.name("core_model_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.aggregated_model.model_id</code>.
     */
    public final TableField<AggregatedModelRecord, UUID> MODEL_ID = createField(DSL.name("model_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.aggregated_model.date_reference_id</code>.
     */
    public final TableField<AggregatedModelRecord, UUID> DATE_REFERENCE_ID = createField(DSL.name("date_reference_id"), SQLDataType.UUID.nullable(false), this, "");

    private AggregatedModel(Name alias, Table<AggregatedModelRecord> aliased) {
        this(alias, aliased, null);
    }

    private AggregatedModel(Name alias, Table<AggregatedModelRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.aggregated_model</code> table reference
     */
    public AggregatedModel(String alias) {
        this(DSL.name(alias), AGGREGATED_MODEL);
    }

    /**
     * Create an aliased <code>public.aggregated_model</code> table reference
     */
    public AggregatedModel(Name alias) {
        this(alias, AGGREGATED_MODEL);
    }

    /**
     * Create a <code>public.aggregated_model</code> table reference
     */
    public AggregatedModel() {
        this(DSL.name("aggregated_model"), null);
    }

    public <O extends Record> AggregatedModel(Table<O> child, ForeignKey<O, AggregatedModelRecord> key) {
        super(child, key, AGGREGATED_MODEL);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<AggregatedModelRecord> getPrimaryKey() {
        return Keys.AGGREGATED_MODEL_PKEY;
    }

    @Override
    public List<ForeignKey<AggregatedModelRecord, ?>> getReferences() {
        return Arrays.asList(Keys.AGGREGATED_MODEL__AGGREGATED_MODEL_ID_FKEY, Keys.AGGREGATED_MODEL__AGGREGATED_MODEL_CORE_MODEL_ID_FKEY, Keys.AGGREGATED_MODEL__AGGREGATED_MODEL_MODEL_ID_FKEY, Keys.AGGREGATED_MODEL__AGGREGATED_MODEL_DATE_REFERENCE_ID_FKEY);
    }

    private transient DataModel _aggregatedModelIdFkey;
    private transient DataSheetModel _dataSheetModel;
    private transient DataModel _aggregatedModelModelIdFkey;
    private transient DataField _dataField;

    /**
     * Get the implicit join path to the <code>public.data_model</code> table,
     * via the <code>aggregated_model_id_fkey</code> key.
     */
    public DataModel aggregatedModelIdFkey() {
        if (_aggregatedModelIdFkey == null)
            _aggregatedModelIdFkey = new DataModel(this, Keys.AGGREGATED_MODEL__AGGREGATED_MODEL_ID_FKEY);

        return _aggregatedModelIdFkey;
    }

    /**
     * Get the implicit join path to the <code>public.data_sheet_model</code>
     * table.
     */
    public DataSheetModel dataSheetModel() {
        if (_dataSheetModel == null)
            _dataSheetModel = new DataSheetModel(this, Keys.AGGREGATED_MODEL__AGGREGATED_MODEL_CORE_MODEL_ID_FKEY);

        return _dataSheetModel;
    }

    /**
     * Get the implicit join path to the <code>public.data_model</code> table,
     * via the <code>aggregated_model_model_id_fkey</code> key.
     */
    public DataModel aggregatedModelModelIdFkey() {
        if (_aggregatedModelModelIdFkey == null)
            _aggregatedModelModelIdFkey = new DataModel(this, Keys.AGGREGATED_MODEL__AGGREGATED_MODEL_MODEL_ID_FKEY);

        return _aggregatedModelModelIdFkey;
    }

    /**
     * Get the implicit join path to the <code>public.data_field</code> table.
     */
    public DataField dataField() {
        if (_dataField == null)
            _dataField = new DataField(this, Keys.AGGREGATED_MODEL__AGGREGATED_MODEL_DATE_REFERENCE_ID_FKEY);

        return _dataField;
    }

    @Override
    public AggregatedModel as(String alias) {
        return new AggregatedModel(DSL.name(alias), this);
    }

    @Override
    public AggregatedModel as(Name alias) {
        return new AggregatedModel(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AggregatedModel rename(String name) {
        return new AggregatedModel(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public AggregatedModel rename(Name name) {
        return new AggregatedModel(name, null);
    }

    // -------------------------------------------------------------------------
    // Row10 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row10<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, UUID, UUID, UUID> fieldsRow() {
        return (Row10) super.fieldsRow();
    }
}
