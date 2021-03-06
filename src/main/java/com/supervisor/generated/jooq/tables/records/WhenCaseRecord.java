/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables.records;


import com.supervisor.generated.jooq.tables.WhenCase;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record12;
import org.jooq.Row12;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WhenCaseRecord extends UpdatableRecordImpl<WhenCaseRecord> implements Record12<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, UUID, UUID, UUID, UUID, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.when_case.creation_date</code>.
     */
    public void setCreationDate(LocalDateTime value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.when_case.creation_date</code>.
     */
    public LocalDateTime getCreationDate() {
        return (LocalDateTime) get(0);
    }

    /**
     * Setter for <code>public.when_case.creator_id</code>.
     */
    public void setCreatorId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.when_case.creator_id</code>.
     */
    public UUID getCreatorId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>public.when_case.last_modification_date</code>.
     */
    public void setLastModificationDate(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.when_case.last_modification_date</code>.
     */
    public LocalDateTime getLastModificationDate() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>public.when_case.last_modifier_id</code>.
     */
    public void setLastModifierId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.when_case.last_modifier_id</code>.
     */
    public UUID getLastModifierId() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>public.when_case.owner_id</code>.
     */
    public void setOwnerId(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.when_case.owner_id</code>.
     */
    public UUID getOwnerId() {
        return (UUID) get(4);
    }

    /**
     * Setter for <code>public.when_case.tag</code>.
     */
    public void setTag(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.when_case.tag</code>.
     */
    public String getTag() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.when_case.id</code>.
     */
    public void setId(UUID value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.when_case.id</code>.
     */
    public UUID getId() {
        return (UUID) get(6);
    }

    /**
     * Setter for <code>public.when_case.left_arg_id</code>.
     */
    public void setLeftArgId(UUID value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.when_case.left_arg_id</code>.
     */
    public UUID getLeftArgId() {
        return (UUID) get(7);
    }

    /**
     * Setter for <code>public.when_case.expression_id</code>.
     */
    public void setExpressionId(UUID value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.when_case.expression_id</code>.
     */
    public UUID getExpressionId() {
        return (UUID) get(8);
    }

    /**
     * Setter for <code>public.when_case.right_arg_id</code>.
     */
    public void setRightArgId(UUID value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.when_case.right_arg_id</code>.
     */
    public UUID getRightArgId() {
        return (UUID) get(9);
    }

    /**
     * Setter for <code>public.when_case.result_id</code>.
     */
    public void setResultId(UUID value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.when_case.result_id</code>.
     */
    public UUID getResultId() {
        return (UUID) get(10);
    }

    /**
     * Setter for <code>public.when_case.comparator</code>.
     */
    public void setComparator(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>public.when_case.comparator</code>.
     */
    public String getComparator() {
        return (String) get(11);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record12 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row12<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, UUID, UUID, UUID, UUID, String> fieldsRow() {
        return (Row12) super.fieldsRow();
    }

    @Override
    public Row12<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, UUID, UUID, UUID, UUID, String> valuesRow() {
        return (Row12) super.valuesRow();
    }

    @Override
    public Field<LocalDateTime> field1() {
        return WhenCase.WHEN_CASE.CREATION_DATE;
    }

    @Override
    public Field<UUID> field2() {
        return WhenCase.WHEN_CASE.CREATOR_ID;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return WhenCase.WHEN_CASE.LAST_MODIFICATION_DATE;
    }

    @Override
    public Field<UUID> field4() {
        return WhenCase.WHEN_CASE.LAST_MODIFIER_ID;
    }

    @Override
    public Field<UUID> field5() {
        return WhenCase.WHEN_CASE.OWNER_ID;
    }

    @Override
    public Field<String> field6() {
        return WhenCase.WHEN_CASE.TAG;
    }

    @Override
    public Field<UUID> field7() {
        return WhenCase.WHEN_CASE.ID;
    }

    @Override
    public Field<UUID> field8() {
        return WhenCase.WHEN_CASE.LEFT_ARG_ID;
    }

    @Override
    public Field<UUID> field9() {
        return WhenCase.WHEN_CASE.EXPRESSION_ID;
    }

    @Override
    public Field<UUID> field10() {
        return WhenCase.WHEN_CASE.RIGHT_ARG_ID;
    }

    @Override
    public Field<UUID> field11() {
        return WhenCase.WHEN_CASE.RESULT_ID;
    }

    @Override
    public Field<String> field12() {
        return WhenCase.WHEN_CASE.COMPARATOR;
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
        return getLeftArgId();
    }

    @Override
    public UUID component9() {
        return getExpressionId();
    }

    @Override
    public UUID component10() {
        return getRightArgId();
    }

    @Override
    public UUID component11() {
        return getResultId();
    }

    @Override
    public String component12() {
        return getComparator();
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
        return getLeftArgId();
    }

    @Override
    public UUID value9() {
        return getExpressionId();
    }

    @Override
    public UUID value10() {
        return getRightArgId();
    }

    @Override
    public UUID value11() {
        return getResultId();
    }

    @Override
    public String value12() {
        return getComparator();
    }

    @Override
    public WhenCaseRecord value1(LocalDateTime value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public WhenCaseRecord value2(UUID value) {
        setCreatorId(value);
        return this;
    }

    @Override
    public WhenCaseRecord value3(LocalDateTime value) {
        setLastModificationDate(value);
        return this;
    }

    @Override
    public WhenCaseRecord value4(UUID value) {
        setLastModifierId(value);
        return this;
    }

    @Override
    public WhenCaseRecord value5(UUID value) {
        setOwnerId(value);
        return this;
    }

    @Override
    public WhenCaseRecord value6(String value) {
        setTag(value);
        return this;
    }

    @Override
    public WhenCaseRecord value7(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public WhenCaseRecord value8(UUID value) {
        setLeftArgId(value);
        return this;
    }

    @Override
    public WhenCaseRecord value9(UUID value) {
        setExpressionId(value);
        return this;
    }

    @Override
    public WhenCaseRecord value10(UUID value) {
        setRightArgId(value);
        return this;
    }

    @Override
    public WhenCaseRecord value11(UUID value) {
        setResultId(value);
        return this;
    }

    @Override
    public WhenCaseRecord value12(String value) {
        setComparator(value);
        return this;
    }

    @Override
    public WhenCaseRecord values(LocalDateTime value1, UUID value2, LocalDateTime value3, UUID value4, UUID value5, String value6, UUID value7, UUID value8, UUID value9, UUID value10, UUID value11, String value12) {
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
        value11(value11);
        value12(value12);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached WhenCaseRecord
     */
    public WhenCaseRecord() {
        super(WhenCase.WHEN_CASE);
    }

    /**
     * Create a detached, initialised WhenCaseRecord
     */
    public WhenCaseRecord(LocalDateTime creationDate, UUID creatorId, LocalDateTime lastModificationDate, UUID lastModifierId, UUID ownerId, String tag, UUID id, UUID leftArgId, UUID expressionId, UUID rightArgId, UUID resultId, String comparator) {
        super(WhenCase.WHEN_CASE);

        setCreationDate(creationDate);
        setCreatorId(creatorId);
        setLastModificationDate(lastModificationDate);
        setLastModifierId(lastModifierId);
        setOwnerId(ownerId);
        setTag(tag);
        setId(id);
        setLeftArgId(leftArgId);
        setExpressionId(expressionId);
        setRightArgId(rightArgId);
        setResultId(resultId);
        setComparator(comparator);
    }
}
