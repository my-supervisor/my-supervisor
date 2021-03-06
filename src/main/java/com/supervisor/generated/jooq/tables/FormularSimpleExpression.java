/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables;


import com.supervisor.generated.jooq.Keys;
import com.supervisor.generated.jooq.Public;
import com.supervisor.generated.jooq.tables.records.FormularSimpleExpressionRecord;

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
public class FormularSimpleExpression extends TableImpl<FormularSimpleExpressionRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.formular_simple_expression</code>
     */
    public static final FormularSimpleExpression FORMULAR_SIMPLE_EXPRESSION = new FormularSimpleExpression();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<FormularSimpleExpressionRecord> getRecordType() {
        return FormularSimpleExpressionRecord.class;
    }

    /**
     * The column <code>public.formular_simple_expression.creation_date</code>.
     */
    public final TableField<FormularSimpleExpressionRecord, LocalDateTime> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.formular_simple_expression.creator_id</code>.
     */
    public final TableField<FormularSimpleExpressionRecord, UUID> CREATOR_ID = createField(DSL.name("creator_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column
     * <code>public.formular_simple_expression.last_modification_date</code>.
     */
    public final TableField<FormularSimpleExpressionRecord, LocalDateTime> LAST_MODIFICATION_DATE = createField(DSL.name("last_modification_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column
     * <code>public.formular_simple_expression.last_modifier_id</code>.
     */
    public final TableField<FormularSimpleExpressionRecord, UUID> LAST_MODIFIER_ID = createField(DSL.name("last_modifier_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.formular_simple_expression.owner_id</code>.
     */
    public final TableField<FormularSimpleExpressionRecord, UUID> OWNER_ID = createField(DSL.name("owner_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.formular_simple_expression.tag</code>.
     */
    public final TableField<FormularSimpleExpressionRecord, String> TAG = createField(DSL.name("tag"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.formular_simple_expression.id</code>.
     */
    public final TableField<FormularSimpleExpressionRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.formular_simple_expression.func</code>.
     */
    public final TableField<FormularSimpleExpressionRecord, String> FUNC = createField(DSL.name("func"), SQLDataType.VARCHAR.nullable(false), this, "");

    private FormularSimpleExpression(Name alias, Table<FormularSimpleExpressionRecord> aliased) {
        this(alias, aliased, null);
    }

    private FormularSimpleExpression(Name alias, Table<FormularSimpleExpressionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.formular_simple_expression</code> table
     * reference
     */
    public FormularSimpleExpression(String alias) {
        this(DSL.name(alias), FORMULAR_SIMPLE_EXPRESSION);
    }

    /**
     * Create an aliased <code>public.formular_simple_expression</code> table
     * reference
     */
    public FormularSimpleExpression(Name alias) {
        this(alias, FORMULAR_SIMPLE_EXPRESSION);
    }

    /**
     * Create a <code>public.formular_simple_expression</code> table reference
     */
    public FormularSimpleExpression() {
        this(DSL.name("formular_simple_expression"), null);
    }

    public <O extends Record> FormularSimpleExpression(Table<O> child, ForeignKey<O, FormularSimpleExpressionRecord> key) {
        super(child, key, FORMULAR_SIMPLE_EXPRESSION);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<FormularSimpleExpressionRecord> getPrimaryKey() {
        return Keys.FORMULAR_SIMPLE_EXPRESSION_PKEY;
    }

    @Override
    public List<ForeignKey<FormularSimpleExpressionRecord, ?>> getReferences() {
        return Arrays.asList(Keys.FORMULAR_SIMPLE_EXPRESSION__FORMULAR_SIMPLE_EXPRESSION_ID_FKEY);
    }

    private transient FormularExpression _formularExpression;

    /**
     * Get the implicit join path to the <code>public.formular_expression</code>
     * table.
     */
    public FormularExpression formularExpression() {
        if (_formularExpression == null)
            _formularExpression = new FormularExpression(this, Keys.FORMULAR_SIMPLE_EXPRESSION__FORMULAR_SIMPLE_EXPRESSION_ID_FKEY);

        return _formularExpression;
    }

    @Override
    public FormularSimpleExpression as(String alias) {
        return new FormularSimpleExpression(DSL.name(alias), this);
    }

    @Override
    public FormularSimpleExpression as(Name alias) {
        return new FormularSimpleExpression(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public FormularSimpleExpression rename(String name) {
        return new FormularSimpleExpression(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public FormularSimpleExpression rename(Name name) {
        return new FormularSimpleExpression(name, null);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String> fieldsRow() {
        return (Row8) super.fieldsRow();
    }
}
