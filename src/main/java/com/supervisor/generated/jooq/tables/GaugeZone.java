/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables;


import com.supervisor.generated.jooq.Keys;
import com.supervisor.generated.jooq.Public;
import com.supervisor.generated.jooq.tables.records.GaugeZoneRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row11;
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
public class GaugeZone extends TableImpl<GaugeZoneRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.gauge_zone</code>
     */
    public static final GaugeZone GAUGE_ZONE = new GaugeZone();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GaugeZoneRecord> getRecordType() {
        return GaugeZoneRecord.class;
    }

    /**
     * The column <code>public.gauge_zone.creation_date</code>.
     */
    public final TableField<GaugeZoneRecord, LocalDateTime> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.gauge_zone.creator_id</code>.
     */
    public final TableField<GaugeZoneRecord, UUID> CREATOR_ID = createField(DSL.name("creator_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.gauge_zone.last_modification_date</code>.
     */
    public final TableField<GaugeZoneRecord, LocalDateTime> LAST_MODIFICATION_DATE = createField(DSL.name("last_modification_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.gauge_zone.last_modifier_id</code>.
     */
    public final TableField<GaugeZoneRecord, UUID> LAST_MODIFIER_ID = createField(DSL.name("last_modifier_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.gauge_zone.owner_id</code>.
     */
    public final TableField<GaugeZoneRecord, UUID> OWNER_ID = createField(DSL.name("owner_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.gauge_zone.tag</code>.
     */
    public final TableField<GaugeZoneRecord, String> TAG = createField(DSL.name("tag"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.gauge_zone.id</code>.
     */
    public final TableField<GaugeZoneRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.gauge_zone.color</code>.
     */
    public final TableField<GaugeZoneRecord, String> COLOR = createField(DSL.name("color"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.gauge_zone.max</code>.
     */
    public final TableField<GaugeZoneRecord, Double> MAX = createField(DSL.name("max"), SQLDataType.DOUBLE.nullable(false), this, "");

    /**
     * The column <code>public.gauge_zone.gauge_id</code>.
     */
    public final TableField<GaugeZoneRecord, UUID> GAUGE_ID = createField(DSL.name("gauge_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.gauge_zone.min</code>.
     */
    public final TableField<GaugeZoneRecord, Double> MIN = createField(DSL.name("min"), SQLDataType.DOUBLE.nullable(false), this, "");

    private GaugeZone(Name alias, Table<GaugeZoneRecord> aliased) {
        this(alias, aliased, null);
    }

    private GaugeZone(Name alias, Table<GaugeZoneRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.gauge_zone</code> table reference
     */
    public GaugeZone(String alias) {
        this(DSL.name(alias), GAUGE_ZONE);
    }

    /**
     * Create an aliased <code>public.gauge_zone</code> table reference
     */
    public GaugeZone(Name alias) {
        this(alias, GAUGE_ZONE);
    }

    /**
     * Create a <code>public.gauge_zone</code> table reference
     */
    public GaugeZone() {
        this(DSL.name("gauge_zone"), null);
    }

    public <O extends Record> GaugeZone(Table<O> child, ForeignKey<O, GaugeZoneRecord> key) {
        super(child, key, GAUGE_ZONE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<GaugeZoneRecord> getPrimaryKey() {
        return Keys.GAUGE_ZONE_PKEY;
    }

    @Override
    public List<ForeignKey<GaugeZoneRecord, ?>> getReferences() {
        return Arrays.asList(Keys.GAUGE_ZONE__GAUGE_ZONE_GAUGE_ID_FKEY);
    }

    private transient GaugeSetting _gaugeSetting;

    /**
     * Get the implicit join path to the <code>public.gauge_setting</code>
     * table.
     */
    public GaugeSetting gaugeSetting() {
        if (_gaugeSetting == null)
            _gaugeSetting = new GaugeSetting(this, Keys.GAUGE_ZONE__GAUGE_ZONE_GAUGE_ID_FKEY);

        return _gaugeSetting;
    }

    @Override
    public GaugeZone as(String alias) {
        return new GaugeZone(DSL.name(alias), this);
    }

    @Override
    public GaugeZone as(Name alias) {
        return new GaugeZone(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public GaugeZone rename(String name) {
        return new GaugeZone(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public GaugeZone rename(Name name) {
        return new GaugeZone(name, null);
    }

    // -------------------------------------------------------------------------
    // Row11 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row11<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, Double, UUID, Double> fieldsRow() {
        return (Row11) super.fieldsRow();
    }
}