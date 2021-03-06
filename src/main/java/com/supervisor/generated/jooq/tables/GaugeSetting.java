/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables;


import com.supervisor.generated.jooq.Keys;
import com.supervisor.generated.jooq.Public;
import com.supervisor.generated.jooq.tables.records.GaugeSettingRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row14;
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
public class GaugeSetting extends TableImpl<GaugeSettingRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.gauge_setting</code>
     */
    public static final GaugeSetting GAUGE_SETTING = new GaugeSetting();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GaugeSettingRecord> getRecordType() {
        return GaugeSettingRecord.class;
    }

    /**
     * The column <code>public.gauge_setting.creation_date</code>.
     */
    public final TableField<GaugeSettingRecord, LocalDateTime> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.gauge_setting.creator_id</code>.
     */
    public final TableField<GaugeSettingRecord, UUID> CREATOR_ID = createField(DSL.name("creator_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.gauge_setting.last_modification_date</code>.
     */
    public final TableField<GaugeSettingRecord, LocalDateTime> LAST_MODIFICATION_DATE = createField(DSL.name("last_modification_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.gauge_setting.last_modifier_id</code>.
     */
    public final TableField<GaugeSettingRecord, UUID> LAST_MODIFIER_ID = createField(DSL.name("last_modifier_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.gauge_setting.owner_id</code>.
     */
    public final TableField<GaugeSettingRecord, UUID> OWNER_ID = createField(DSL.name("owner_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.gauge_setting.tag</code>.
     */
    public final TableField<GaugeSettingRecord, String> TAG = createField(DSL.name("tag"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.gauge_setting.id</code>.
     */
    public final TableField<GaugeSettingRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.gauge_setting.minor_ticks</code>.
     */
    public final TableField<GaugeSettingRecord, Integer> MINOR_TICKS = createField(DSL.name("minor_ticks"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.gauge_setting.symbol_position</code>.
     */
    public final TableField<GaugeSettingRecord, String> SYMBOL_POSITION = createField(DSL.name("symbol_position"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.gauge_setting.max</code>.
     */
    public final TableField<GaugeSettingRecord, Double> MAX = createField(DSL.name("max"), SQLDataType.DOUBLE.nullable(false), this, "");

    /**
     * The column <code>public.gauge_setting.min</code>.
     */
    public final TableField<GaugeSettingRecord, Double> MIN = createField(DSL.name("min"), SQLDataType.DOUBLE.nullable(false), this, "");

    /**
     * The column <code>public.gauge_setting.gauge_type</code>.
     */
    public final TableField<GaugeSettingRecord, String> GAUGE_TYPE = createField(DSL.name("gauge_type"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.gauge_setting.unity_symbol</code>.
     */
    public final TableField<GaugeSettingRecord, String> UNITY_SYMBOL = createField(DSL.name("unity_symbol"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.gauge_setting.major_ticks</code>.
     */
    public final TableField<GaugeSettingRecord, Integer> MAJOR_TICKS = createField(DSL.name("major_ticks"), SQLDataType.INTEGER.nullable(false), this, "");

    private GaugeSetting(Name alias, Table<GaugeSettingRecord> aliased) {
        this(alias, aliased, null);
    }

    private GaugeSetting(Name alias, Table<GaugeSettingRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.gauge_setting</code> table reference
     */
    public GaugeSetting(String alias) {
        this(DSL.name(alias), GAUGE_SETTING);
    }

    /**
     * Create an aliased <code>public.gauge_setting</code> table reference
     */
    public GaugeSetting(Name alias) {
        this(alias, GAUGE_SETTING);
    }

    /**
     * Create a <code>public.gauge_setting</code> table reference
     */
    public GaugeSetting() {
        this(DSL.name("gauge_setting"), null);
    }

    public <O extends Record> GaugeSetting(Table<O> child, ForeignKey<O, GaugeSettingRecord> key) {
        super(child, key, GAUGE_SETTING);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<GaugeSettingRecord> getPrimaryKey() {
        return Keys.GAUGE_SETTING_PKEY;
    }

    @Override
    public List<ForeignKey<GaugeSettingRecord, ?>> getReferences() {
        return Arrays.asList(Keys.GAUGE_SETTING__GAUGE_SETTING_ID_FKEY);
    }

    private transient IndicatorGeneralSetting _indicatorGeneralSetting;

    /**
     * Get the implicit join path to the
     * <code>public.indicator_general_setting</code> table.
     */
    public IndicatorGeneralSetting indicatorGeneralSetting() {
        if (_indicatorGeneralSetting == null)
            _indicatorGeneralSetting = new IndicatorGeneralSetting(this, Keys.GAUGE_SETTING__GAUGE_SETTING_ID_FKEY);

        return _indicatorGeneralSetting;
    }

    @Override
    public GaugeSetting as(String alias) {
        return new GaugeSetting(DSL.name(alias), this);
    }

    @Override
    public GaugeSetting as(Name alias) {
        return new GaugeSetting(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public GaugeSetting rename(String name) {
        return new GaugeSetting(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public GaugeSetting rename(Name name) {
        return new GaugeSetting(name, null);
    }

    // -------------------------------------------------------------------------
    // Row14 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row14<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, Integer, String, Double, Double, String, String, Integer> fieldsRow() {
        return (Row14) super.fieldsRow();
    }
}
