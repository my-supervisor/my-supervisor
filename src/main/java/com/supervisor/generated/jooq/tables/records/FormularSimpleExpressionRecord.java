/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables.records;


import com.supervisor.generated.jooq.tables.FormularSimpleExpression;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class FormularSimpleExpressionRecord extends UpdatableRecordImpl<FormularSimpleExpressionRecord> implements Record8<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.formular_simple_expression.creation_date</code>.
     */
    public void setCreationDate(LocalDateTime value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.formular_simple_expression.creation_date</code>.
     */
    public LocalDateTime getCreationDate() {
        return (LocalDateTime) get(0);
    }

    /**
     * Setter for <code>public.formular_simple_expression.creator_id</code>.
     */
    public void setCreatorId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.formular_simple_expression.creator_id</code>.
     */
    public UUID getCreatorId() {
        return (UUID) get(1);
    }

    /**
     * Setter for
     * <code>public.formular_simple_expression.last_modification_date</code>.
     */
    public void setLastModificationDate(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for
     * <code>public.formular_simple_expression.last_modification_date</code>.
     */
    public LocalDateTime getLastModificationDate() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for
     * <code>public.formular_simple_expression.last_modifier_id</code>.
     */
    public void setLastModifierId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for
     * <code>public.formular_simple_expression.last_modifier_id</code>.
     */
    public UUID getLastModifierId() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>public.formular_simple_expression.owner_id</code>.
     */
    public void setOwnerId(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.formular_simple_expression.owner_id</code>.
     */
    public UUID getOwnerId() {
        return (UUID) get(4);
    }

    /**
     * Setter for <code>public.formular_simple_expression.tag</code>.
     */
    public void setTag(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.formular_simple_expression.tag</code>.
     */
    public String getTag() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.formular_simple_expression.id</code>.
     */
    public void setId(UUID value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.formular_simple_expression.id</code>.
     */
    public UUID getId() {
        return (UUID) get(6);
    }

    /**
     * Setter for <code>public.formular_simple_expression.func</code>.
     */
    public void setFunc(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.formular_simple_expression.func</code>.
     */
    public String getFunc() {
        return (String) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row8<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    @Override
    public Row8<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String> valuesRow() {
        return (Row8) super.valuesRow();
    }

    @Override
    public Field<LocalDateTime> field1() {
        return FormularSimpleExpression.FORMULAR_SIMPLE_EXPRESSION.CREATION_DATE;
    }

    @Override
    public Field<UUID> field2() {
        return FormularSimpleExpression.FORMULAR_SIMPLE_EXPRESSION.CREATOR_ID;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return FormularSimpleExpression.FORMULAR_SIMPLE_EXPRESSION.LAST_MODIFICATION_DATE;
    }

    @Override
    public Field<UUID> field4() {
        return FormularSimpleExpression.FORMULAR_SIMPLE_EXPRESSION.LAST_MODIFIER_ID;
    }

    @Override
    public Field<UUID> field5() {
        return FormularSimpleExpression.FORMULAR_SIMPLE_EXPRESSION.OWNER_ID;
    }

    @Override
    public Field<String> field6() {
        return FormularSimpleExpression.FORMULAR_SIMPLE_EXPRESSION.TAG;
    }

    @Override
    public Field<UUID> field7() {
        return FormularSimpleExpression.FORMULAR_SIMPLE_EXPRESSION.ID;
    }

    @Override
    public Field<String> field8() {
        return FormularSimpleExpression.FORMULAR_SIMPLE_EXPRESSION.FUNC;
    }

    @Override
    public LocalDateTime component1() {
        return getCreationDate();
    }

    @Override
    public UUID component2() {
        return getCreatorId();
    }

    @Override
    public LocalDateTime component3() {
        return getLastModificationDate();
    }

    @Override
    public UUID component4() {
        return getLastModifierId();
    }

    @Override
    public UUID component5() {
        return getOwnerId();
    }

    @Override
    public String component6() {
        return getTag();
    }

    @Override
    public UUID component7() {
        return getId();
    }

    @Override
    public String component8() {
        return getFunc();
    }

    @Override
    public LocalDateTime value1() {
        return getCreationDate();
    }

    @Override
    public UUID value2() {
        return getCreatorId();
    }

    @Override
    public LocalDateTime value3() {
        return getLastModificationDate();
    }

    @Override
    public UUID value4() {
        return getLastModifierId();
    }

    @Override
    public UUID value5() {
        return getOwnerId();
    }

    @Override
    public String value6() {
        return getTag();
    }

    @Override
    public UUID value7() {
        return getId();
    }

    @Override
    public String value8() {
        return getFunc();
    }

    @Override
    public FormularSimpleExpressionRecord value1(LocalDateTime value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public FormularSimpleExpressionRecord value2(UUID value) {
        setCreatorId(value);
        return this;
    }

    @Override
    public FormularSimpleExpressionRecord value3(LocalDateTime value) {
        setLastModificationDate(value);
        return this;
    }

    @Override
    public FormularSimpleExpressionRecord value4(UUID value) {
        setLastModifierId(value);
        return this;
    }

    @Override
    public FormularSimpleExpressionRecord value5(UUID value) {
        setOwnerId(value);
        return this;
    }

    @Override
    public FormularSimpleExpressionRecord value6(String value) {
        setTag(value);
        return this;
    }

    @Override
    public FormularSimpleExpressionRecord value7(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public FormularSimpleExpressionRecord value8(String value) {
        setFunc(value);
        return this;
    }

    @Override
    public FormularSimpleExpressionRecord values(LocalDateTime value1, UUID value2, LocalDateTime value3, UUID value4, UUID value5, String value6, UUID value7, String value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached FormularSimpleExpressionRecord
     */
    public FormularSimpleExpressionRecord() {
        super(FormularSimpleExpression.FORMULAR_SIMPLE_EXPRESSION);
    }

    /**
     * Create a detached, initialised FormularSimpleExpressionRecord
     */
    public FormularSimpleExpressionRecord(LocalDateTime creationDate, UUID creatorId, LocalDateTime lastModificationDate, UUID lastModifierId, UUID ownerId, String tag, UUID id, String func) {
        super(FormularSimpleExpression.FORMULAR_SIMPLE_EXPRESSION);

        setCreationDate(creationDate);
        setCreatorId(creatorId);
        setLastModificationDate(lastModificationDate);
        setLastModifierId(lastModifierId);
        setOwnerId(ownerId);
        setTag(tag);
        setId(id);
        setFunc(func);
    }
}
