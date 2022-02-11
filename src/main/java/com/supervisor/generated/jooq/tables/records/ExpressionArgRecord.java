/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables.records;


import com.supervisor.generated.jooq.tables.ExpressionArg;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ExpressionArgRecord extends UpdatableRecordImpl<ExpressionArgRecord> implements Record10<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, UUID, String, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.expression_arg.creation_date</code>.
     */
    public void setCreationDate(LocalDateTime value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.expression_arg.creation_date</code>.
     */
    public LocalDateTime getCreationDate() {
        return (LocalDateTime) get(0);
    }

    /**
     * Setter for <code>public.expression_arg.creator_id</code>.
     */
    public void setCreatorId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.expression_arg.creator_id</code>.
     */
    public UUID getCreatorId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>public.expression_arg.last_modification_date</code>.
     */
    public void setLastModificationDate(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.expression_arg.last_modification_date</code>.
     */
    public LocalDateTime getLastModificationDate() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>public.expression_arg.last_modifier_id</code>.
     */
    public void setLastModifierId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.expression_arg.last_modifier_id</code>.
     */
    public UUID getLastModifierId() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>public.expression_arg.owner_id</code>.
     */
    public void setOwnerId(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.expression_arg.owner_id</code>.
     */
    public UUID getOwnerId() {
        return (UUID) get(4);
    }

    /**
     * Setter for <code>public.expression_arg.tag</code>.
     */
    public void setTag(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.expression_arg.tag</code>.
     */
    public String getTag() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.expression_arg.id</code>.
     */
    public void setId(UUID value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.expression_arg.id</code>.
     */
    public UUID getId() {
        return (UUID) get(6);
    }

    /**
     * Setter for <code>public.expression_arg.expression_id</code>.
     */
    public void setExpressionId(UUID value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.expression_arg.expression_id</code>.
     */
    public UUID getExpressionId() {
        return (UUID) get(7);
    }

    /**
     * Setter for <code>public.expression_arg.type</code>.
     */
    public void setType(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.expression_arg.type</code>.
     */
    public String getType() {
        return (String) get(8);
    }

    /**
     * Setter for <code>public.expression_arg.no</code>.
     */
    public void setNo(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.expression_arg.no</code>.
     */
    public Integer getNo() {
        return (Integer) get(9);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record10 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row10<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, UUID, String, Integer> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    @Override
    public Row10<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, UUID, String, Integer> valuesRow() {
        return (Row10) super.valuesRow();
    }

    @Override
    public Field<LocalDateTime> field1() {
        return ExpressionArg.EXPRESSION_ARG.CREATION_DATE;
    }

    @Override
    public Field<UUID> field2() {
        return ExpressionArg.EXPRESSION_ARG.CREATOR_ID;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return ExpressionArg.EXPRESSION_ARG.LAST_MODIFICATION_DATE;
    }

    @Override
    public Field<UUID> field4() {
        return ExpressionArg.EXPRESSION_ARG.LAST_MODIFIER_ID;
    }

    @Override
    public Field<UUID> field5() {
        return ExpressionArg.EXPRESSION_ARG.OWNER_ID;
    }

    @Override
    public Field<String> field6() {
        return ExpressionArg.EXPRESSION_ARG.TAG;
    }

    @Override
    public Field<UUID> field7() {
        return ExpressionArg.EXPRESSION_ARG.ID;
    }

    @Override
    public Field<UUID> field8() {
        return ExpressionArg.EXPRESSION_ARG.EXPRESSION_ID;
    }

    @Override
    public Field<String> field9() {
        return ExpressionArg.EXPRESSION_ARG.TYPE;
    }

    @Override
    public Field<Integer> field10() {
        return ExpressionArg.EXPRESSION_ARG.NO;
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
    public UUID component8() {
        return getExpressionId();
    }

    @Override
    public String component9() {
        return getType();
    }

    @Override
    public Integer component10() {
        return getNo();
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
    public UUID value8() {
        return getExpressionId();
    }

    @Override
    public String value9() {
        return getType();
    }

    @Override
    public Integer value10() {
        return getNo();
    }

    @Override
    public ExpressionArgRecord value1(LocalDateTime value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public ExpressionArgRecord value2(UUID value) {
        setCreatorId(value);
        return this;
    }

    @Override
    public ExpressionArgRecord value3(LocalDateTime value) {
        setLastModificationDate(value);
        return this;
    }

    @Override
    public ExpressionArgRecord value4(UUID value) {
        setLastModifierId(value);
        return this;
    }

    @Override
    public ExpressionArgRecord value5(UUID value) {
        setOwnerId(value);
        return this;
    }

    @Override
    public ExpressionArgRecord value6(String value) {
        setTag(value);
        return this;
    }

    @Override
    public ExpressionArgRecord value7(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public ExpressionArgRecord value8(UUID value) {
        setExpressionId(value);
        return this;
    }

    @Override
    public ExpressionArgRecord value9(String value) {
        setType(value);
        return this;
    }

    @Override
    public ExpressionArgRecord value10(Integer value) {
        setNo(value);
        return this;
    }

    @Override
    public ExpressionArgRecord values(LocalDateTime value1, UUID value2, LocalDateTime value3, UUID value4, UUID value5, String value6, UUID value7, UUID value8, String value9, Integer value10) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ExpressionArgRecord
     */
    public ExpressionArgRecord() {
        super(ExpressionArg.EXPRESSION_ARG);
    }

    /**
     * Create a detached, initialised ExpressionArgRecord
     */
    public ExpressionArgRecord(LocalDateTime creationDate, UUID creatorId, LocalDateTime lastModificationDate, UUID lastModifierId, UUID ownerId, String tag, UUID id, UUID expressionId, String type, Integer no) {
        super(ExpressionArg.EXPRESSION_ARG);

        setCreationDate(creationDate);
        setCreatorId(creatorId);
        setLastModificationDate(lastModificationDate);
        setLastModifierId(lastModifierId);
        setOwnerId(ownerId);
        setTag(tag);
        setId(id);
        setExpressionId(expressionId);
        setType(type);
        setNo(no);
    }
}
