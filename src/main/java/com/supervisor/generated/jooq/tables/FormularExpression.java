/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables;


import com.supervisor.generated.jooq.Keys;
import com.supervisor.generated.jooq.Public;
import com.supervisor.generated.jooq.tables.records.FormularExpressionRecord;

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
public class FormularExpression extends TableImpl<FormularExpressionRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.formular_expression</code>
     */
    public static final FormularExpression FORMULAR_EXPRESSION = new FormularExpression();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<FormularExpressionRecord> getRecordType() {
        return FormularExpressionRecord.class;
    }

    /**
     * The column <code>public.formular_expression.creation_date</code>.
     */
    public final TableField<FormularExpressionRecord, LocalDateTime> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.formular_expression.creator_id</code>.
     */
    public final TableField<FormularExpressionRecord, UUID> CREATOR_ID = createField(DSL.name("creator_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column
     * <code>public.formular_expression.last_modification_date</code>.
     */
    public final TableField<FormularExpressionRecord, LocalDateTime> LAST_MODIFICATION_DATE = createField(DSL.name("last_modification_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.formular_expression.last_modifier_id</code>.
     */
    public final TableField<FormularExpressionRecord, UUID> LAST_MODIFIER_ID = createField(DSL.name("last_modifier_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.formular_expression.owner_id</code>.
     */
    public final TableField<FormularExpressionRecord, UUID> OWNER_ID = createField(DSL.name("owner_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.formular_expression.tag</code>.
     */
    public final TableField<FormularExpressionRecord, String> TAG = createField(DSL.name("tag"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.formular_expression.id</code>.
     */
    public final TableField<FormularExpressionRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.formular_expression.formular_id</code>.
     */
    public final TableField<FormularExpressionRecord, UUID> FORMULAR_ID = createField(DSL.name("formular_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.formular_expression.no</code>.
     */
    public final TableField<FormularExpressionRecord, Integer> NO = createField(DSL.name("no"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.formular_expression.type</code>.
     */
    public final TableField<FormularExpressionRecord, String> TYPE = createField(DSL.name("type"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column
     * <code>public.formular_expression.extended_model_aggregate</code>.
     */
    public final TableField<FormularExpressionRecord, String> EXTENDED_MODEL_AGGREGATE = createField(DSL.name("extended_model_aggregate"), SQLDataType.VARCHAR, this, "");

    /**
     * The column
     * <code>public.formular_expression.extended_child_aggregate</code>.
     */
    public final TableField<FormularExpressionRecord, String> EXTENDED_CHILD_AGGREGATE = createField(DSL.name("extended_child_aggregate"), SQLDataType.VARCHAR, this, "");

    private FormularExpression(Name alias, Table<FormularExpressionRecord> aliased) {
        this(alias, aliased, null);
    }

    private FormularExpression(Name alias, Table<FormularExpressionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.formular_expression</code> table reference
     */
    public FormularExpression(String alias) {
        this(DSL.name(alias), FORMULAR_EXPRESSION);
    }

    /**
     * Create an aliased <code>public.formular_expression</code> table reference
     */
    public FormularExpression(Name alias) {
        this(alias, FORMULAR_EXPRESSION);
    }

    /**
     * Create a <code>public.formular_expression</code> table reference
     */
    public FormularExpression() {
        this(DSL.name("formular_expression"), null);
    }

    public <O extends Record> FormularExpression(Table<O> child, ForeignKey<O, FormularExpressionRecord> key) {
        super(child, key, FORMULAR_EXPRESSION);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<FormularExpressionRecord> getPrimaryKey() {
        return Keys.FORMULAR_EXPRESSION_PKEY;
    }

    @Override
    public List<ForeignKey<FormularExpressionRecord, ?>> getReferences() {
        return Arrays.asList(Keys.FORMULAR_EXPRESSION__FORMULAR_EXPRESSION_FORMULAR_ID_FKEY);
    }

    private transient FormularDataField _formularDataField;

    /**
     * Get the implicit join path to the <code>public.formular_data_field</code>
     * table.
     */
    public FormularDataField formularDataField() {
        if (_formularDataField == null)
            _formularDataField = new FormularDataField(this, Keys.FORMULAR_EXPRESSION__FORMULAR_EXPRESSION_FORMULAR_ID_FKEY);

        return _formularDataField;
    }

    @Override
    public FormularExpression as(String alias) {
        return new FormularExpression(DSL.name(alias), this);
    }

    @Override
    public FormularExpression as(Name alias) {
        return new FormularExpression(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public FormularExpression rename(String name) {
        return new FormularExpression(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public FormularExpression rename(Name name) {
        return new FormularExpression(name, null);
    }

    // -------------------------------------------------------------------------
    // Row12 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row12<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, UUID, Integer, String, String, String> fieldsRow() {
        return (Row12) super.fieldsRow();
    }
}
