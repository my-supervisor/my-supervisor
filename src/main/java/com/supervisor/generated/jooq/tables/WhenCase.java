/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables;


import com.supervisor.generated.jooq.Keys;
import com.supervisor.generated.jooq.Public;
import com.supervisor.generated.jooq.tables.records.WhenCaseRecord;

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
public class WhenCase extends TableImpl<WhenCaseRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.when_case</code>
     */
    public static final WhenCase WHEN_CASE = new WhenCase();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<WhenCaseRecord> getRecordType() {
        return WhenCaseRecord.class;
    }

    /**
     * The column <code>public.when_case.creation_date</code>.
     */
    public final TableField<WhenCaseRecord, LocalDateTime> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.when_case.creator_id</code>.
     */
    public final TableField<WhenCaseRecord, UUID> CREATOR_ID = createField(DSL.name("creator_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.when_case.last_modification_date</code>.
     */
    public final TableField<WhenCaseRecord, LocalDateTime> LAST_MODIFICATION_DATE = createField(DSL.name("last_modification_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.when_case.last_modifier_id</code>.
     */
    public final TableField<WhenCaseRecord, UUID> LAST_MODIFIER_ID = createField(DSL.name("last_modifier_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.when_case.owner_id</code>.
     */
    public final TableField<WhenCaseRecord, UUID> OWNER_ID = createField(DSL.name("owner_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.when_case.tag</code>.
     */
    public final TableField<WhenCaseRecord, String> TAG = createField(DSL.name("tag"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.when_case.id</code>.
     */
    public final TableField<WhenCaseRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.when_case.left_arg_id</code>.
     */
    public final TableField<WhenCaseRecord, UUID> LEFT_ARG_ID = createField(DSL.name("left_arg_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.when_case.expression_id</code>.
     */
    public final TableField<WhenCaseRecord, UUID> EXPRESSION_ID = createField(DSL.name("expression_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.when_case.right_arg_id</code>.
     */
    public final TableField<WhenCaseRecord, UUID> RIGHT_ARG_ID = createField(DSL.name("right_arg_id"), SQLDataType.UUID, this, "");

    /**
     * The column <code>public.when_case.result_id</code>.
     */
    public final TableField<WhenCaseRecord, UUID> RESULT_ID = createField(DSL.name("result_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.when_case.comparator</code>.
     */
    public final TableField<WhenCaseRecord, String> COMPARATOR = createField(DSL.name("comparator"), SQLDataType.VARCHAR.nullable(false), this, "");

    private WhenCase(Name alias, Table<WhenCaseRecord> aliased) {
        this(alias, aliased, null);
    }

    private WhenCase(Name alias, Table<WhenCaseRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.when_case</code> table reference
     */
    public WhenCase(String alias) {
        this(DSL.name(alias), WHEN_CASE);
    }

    /**
     * Create an aliased <code>public.when_case</code> table reference
     */
    public WhenCase(Name alias) {
        this(alias, WHEN_CASE);
    }

    /**
     * Create a <code>public.when_case</code> table reference
     */
    public WhenCase() {
        this(DSL.name("when_case"), null);
    }

    public <O extends Record> WhenCase(Table<O> child, ForeignKey<O, WhenCaseRecord> key) {
        super(child, key, WHEN_CASE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<WhenCaseRecord> getPrimaryKey() {
        return Keys.WHEN_CASE_PKEY;
    }

    @Override
    public List<ForeignKey<WhenCaseRecord, ?>> getReferences() {
        return Arrays.asList(Keys.WHEN_CASE__WHEN_CASE_LEFT_ARG_ID_FKEY, Keys.WHEN_CASE__WHEN_CASE_EXPRESSION_ID_FKEY, Keys.WHEN_CASE__WHEN_CASE_RIGHT_ARG_ID_FKEY, Keys.WHEN_CASE__WHEN_CASE_RESULT_ID_FKEY);
    }

    private transient ExpressionArg _whenCaseLeftArgIdFkey;
    private transient FormularCaseExpression _formularCaseExpression;
    private transient ExpressionArg _whenCaseRightArgIdFkey;
    private transient ExpressionArg _whenCaseResultIdFkey;

    /**
     * Get the implicit join path to the <code>public.expression_arg</code>
     * table, via the <code>when_case_left_arg_id_fkey</code> key.
     */
    public ExpressionArg whenCaseLeftArgIdFkey() {
        if (_whenCaseLeftArgIdFkey == null)
            _whenCaseLeftArgIdFkey = new ExpressionArg(this, Keys.WHEN_CASE__WHEN_CASE_LEFT_ARG_ID_FKEY);

        return _whenCaseLeftArgIdFkey;
    }

    /**
     * Get the implicit join path to the
     * <code>public.formular_case_expression</code> table.
     */
    public FormularCaseExpression formularCaseExpression() {
        if (_formularCaseExpression == null)
            _formularCaseExpression = new FormularCaseExpression(this, Keys.WHEN_CASE__WHEN_CASE_EXPRESSION_ID_FKEY);

        return _formularCaseExpression;
    }

    /**
     * Get the implicit join path to the <code>public.expression_arg</code>
     * table, via the <code>when_case_right_arg_id_fkey</code> key.
     */
    public ExpressionArg whenCaseRightArgIdFkey() {
        if (_whenCaseRightArgIdFkey == null)
            _whenCaseRightArgIdFkey = new ExpressionArg(this, Keys.WHEN_CASE__WHEN_CASE_RIGHT_ARG_ID_FKEY);

        return _whenCaseRightArgIdFkey;
    }

    /**
     * Get the implicit join path to the <code>public.expression_arg</code>
     * table, via the <code>when_case_result_id_fkey</code> key.
     */
    public ExpressionArg whenCaseResultIdFkey() {
        if (_whenCaseResultIdFkey == null)
            _whenCaseResultIdFkey = new ExpressionArg(this, Keys.WHEN_CASE__WHEN_CASE_RESULT_ID_FKEY);

        return _whenCaseResultIdFkey;
    }

    @Override
    public WhenCase as(String alias) {
        return new WhenCase(DSL.name(alias), this);
    }

    @Override
    public WhenCase as(Name alias) {
        return new WhenCase(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public WhenCase rename(String name) {
        return new WhenCase(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public WhenCase rename(Name name) {
        return new WhenCase(name, null);
    }

    // -------------------------------------------------------------------------
    // Row12 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row12<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, UUID, UUID, UUID, UUID, String> fieldsRow() {
        return (Row12) super.fieldsRow();
    }
}
