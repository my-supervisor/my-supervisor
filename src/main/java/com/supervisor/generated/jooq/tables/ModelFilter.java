/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables;


import com.supervisor.generated.jooq.Keys;
import com.supervisor.generated.jooq.Public;
import com.supervisor.generated.jooq.tables.records.ModelFilterRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row8;
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
public class ModelFilter extends TableImpl<ModelFilterRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.model_filter</code>
     */
    public static final ModelFilter MODEL_FILTER = new ModelFilter();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ModelFilterRecord> getRecordType() {
        return ModelFilterRecord.class;
    }

    /**
     * The column <code>public.model_filter.creation_date</code>.
     */
    public final TableField<ModelFilterRecord, LocalDateTime> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.model_filter.creator_id</code>.
     */
    public final TableField<ModelFilterRecord, UUID> CREATOR_ID = createField(DSL.name("creator_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.model_filter.last_modification_date</code>.
     */
    public final TableField<ModelFilterRecord, LocalDateTime> LAST_MODIFICATION_DATE = createField(DSL.name("last_modification_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.model_filter.last_modifier_id</code>.
     */
    public final TableField<ModelFilterRecord, UUID> LAST_MODIFIER_ID = createField(DSL.name("last_modifier_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.model_filter.owner_id</code>.
     */
    public final TableField<ModelFilterRecord, UUID> OWNER_ID = createField(DSL.name("owner_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.model_filter.tag</code>.
     */
    public final TableField<ModelFilterRecord, String> TAG = createField(DSL.name("tag"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.model_filter.id</code>.
     */
    public final TableField<ModelFilterRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.model_filter.model_id</code>.
     */
    public final TableField<ModelFilterRecord, UUID> MODEL_ID = createField(DSL.name("model_id"), SQLDataType.UUID.nullable(false), this, "");

    private ModelFilter(Name alias, Table<ModelFilterRecord> aliased) {
        this(alias, aliased, null);
    }

    private ModelFilter(Name alias, Table<ModelFilterRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.model_filter</code> table reference
     */
    public ModelFilter(String alias) {
        this(DSL.name(alias), MODEL_FILTER);
    }

    /**
     * Create an aliased <code>public.model_filter</code> table reference
     */
    public ModelFilter(Name alias) {
        this(alias, MODEL_FILTER);
    }

    /**
     * Create a <code>public.model_filter</code> table reference
     */
    public ModelFilter() {
        this(DSL.name("model_filter"), null);
    }

    public <O extends Record> ModelFilter(Table<O> child, ForeignKey<O, ModelFilterRecord> key) {
        super(child, key, MODEL_FILTER);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<ModelFilterRecord> getPrimaryKey() {
        return Keys.MODEL_FILTER_PKEY;
    }

    @Override
    public List<ForeignKey<ModelFilterRecord, ?>> getReferences() {
        return Arrays.asList(Keys.MODEL_FILTER__MODEL_FILTER_MODEL_ID_FKEY);
    }

    private transient AggregatedModel _aggregatedModel;

    /**
     * Get the implicit join path to the <code>public.aggregated_model</code>
     * table.
     */
    public AggregatedModel aggregatedModel() {
        if (_aggregatedModel == null)
            _aggregatedModel = new AggregatedModel(this, Keys.MODEL_FILTER__MODEL_FILTER_MODEL_ID_FKEY);

        return _aggregatedModel;
    }

    @Override
    public ModelFilter as(String alias) {
        return new ModelFilter(DSL.name(alias), this);
    }

    @Override
    public ModelFilter as(Name alias) {
        return new ModelFilter(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ModelFilter rename(String name) {
        return new ModelFilter(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ModelFilter rename(Name name) {
        return new ModelFilter(name, null);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, UUID> fieldsRow() {
        return (Row8) super.fieldsRow();
    }
}