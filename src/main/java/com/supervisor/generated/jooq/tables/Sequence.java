/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables;


import com.supervisor.generated.jooq.Keys;
import com.supervisor.generated.jooq.Public;
import com.supervisor.generated.jooq.tables.records.SequenceRecord;

import java.time.LocalDateTime;
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
public class Sequence extends TableImpl<SequenceRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.sequence</code>
     */
    public static final Sequence SEQUENCE = new Sequence();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SequenceRecord> getRecordType() {
        return SequenceRecord.class;
    }

    /**
     * The column <code>public.sequence.creation_date</code>.
     */
    public final TableField<SequenceRecord, LocalDateTime> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.sequence.creator_id</code>.
     */
    public final TableField<SequenceRecord, UUID> CREATOR_ID = createField(DSL.name("creator_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.sequence.last_modification_date</code>.
     */
    public final TableField<SequenceRecord, LocalDateTime> LAST_MODIFICATION_DATE = createField(DSL.name("last_modification_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.sequence.last_modifier_id</code>.
     */
    public final TableField<SequenceRecord, UUID> LAST_MODIFIER_ID = createField(DSL.name("last_modifier_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.sequence.owner_id</code>.
     */
    public final TableField<SequenceRecord, UUID> OWNER_ID = createField(DSL.name("owner_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.sequence.tag</code>.
     */
    public final TableField<SequenceRecord, String> TAG = createField(DSL.name("tag"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.sequence.id</code>.
     */
    public final TableField<SequenceRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.sequence.step</code>.
     */
    public final TableField<SequenceRecord, Integer> STEP = createField(DSL.name("step"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.sequence.next_number</code>.
     */
    public final TableField<SequenceRecord, Long> NEXT_NUMBER = createField(DSL.name("next_number"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.sequence.size</code>.
     */
    public final TableField<SequenceRecord, Integer> SIZE = createField(DSL.name("size"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.sequence.name</code>.
     */
    public final TableField<SequenceRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.sequence.prefix</code>.
     */
    public final TableField<SequenceRecord, String> PREFIX = createField(DSL.name("prefix"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.sequence.suffix</code>.
     */
    public final TableField<SequenceRecord, String> SUFFIX = createField(DSL.name("suffix"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.sequence.code</code>.
     */
    public final TableField<SequenceRecord, String> CODE = createField(DSL.name("code"), SQLDataType.VARCHAR.nullable(false), this, "");

    private Sequence(Name alias, Table<SequenceRecord> aliased) {
        this(alias, aliased, null);
    }

    private Sequence(Name alias, Table<SequenceRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.sequence</code> table reference
     */
    public Sequence(String alias) {
        this(DSL.name(alias), SEQUENCE);
    }

    /**
     * Create an aliased <code>public.sequence</code> table reference
     */
    public Sequence(Name alias) {
        this(alias, SEQUENCE);
    }

    /**
     * Create a <code>public.sequence</code> table reference
     */
    public Sequence() {
        this(DSL.name("sequence"), null);
    }

    public <O extends Record> Sequence(Table<O> child, ForeignKey<O, SequenceRecord> key) {
        super(child, key, SEQUENCE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<SequenceRecord> getPrimaryKey() {
        return Keys.SEQUENCE_PKEY;
    }

    @Override
    public Sequence as(String alias) {
        return new Sequence(DSL.name(alias), this);
    }

    @Override
    public Sequence as(Name alias) {
        return new Sequence(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Sequence rename(String name) {
        return new Sequence(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Sequence rename(Name name) {
        return new Sequence(name, null);
    }

    // -------------------------------------------------------------------------
    // Row14 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row14<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, Integer, Long, Integer, String, String, String, String> fieldsRow() {
        return (Row14) super.fieldsRow();
    }
}
