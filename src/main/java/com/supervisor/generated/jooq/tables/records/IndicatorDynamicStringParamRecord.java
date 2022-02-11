/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables.records;


import com.supervisor.generated.jooq.tables.IndicatorDynamicStringParam;

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
public class IndicatorDynamicStringParamRecord extends UpdatableRecordImpl<IndicatorDynamicStringParamRecord> implements Record8<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for
     * <code>public.indicator_dynamic_string_param.creation_date</code>.
     */
    public void setCreationDate(LocalDateTime value) {
        set(0, value);
    }

    /**
     * Getter for
     * <code>public.indicator_dynamic_string_param.creation_date</code>.
     */
    public LocalDateTime getCreationDate() {
        return (LocalDateTime) get(0);
    }

    /**
     * Setter for <code>public.indicator_dynamic_string_param.creator_id</code>.
     */
    public void setCreatorId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.indicator_dynamic_string_param.creator_id</code>.
     */
    public UUID getCreatorId() {
        return (UUID) get(1);
    }

    /**
     * Setter for
     * <code>public.indicator_dynamic_string_param.last_modification_date</code>.
     */
    public void setLastModificationDate(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for
     * <code>public.indicator_dynamic_string_param.last_modification_date</code>.
     */
    public LocalDateTime getLastModificationDate() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for
     * <code>public.indicator_dynamic_string_param.last_modifier_id</code>.
     */
    public void setLastModifierId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for
     * <code>public.indicator_dynamic_string_param.last_modifier_id</code>.
     */
    public UUID getLastModifierId() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>public.indicator_dynamic_string_param.owner_id</code>.
     */
    public void setOwnerId(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.indicator_dynamic_string_param.owner_id</code>.
     */
    public UUID getOwnerId() {
        return (UUID) get(4);
    }

    /**
     * Setter for <code>public.indicator_dynamic_string_param.tag</code>.
     */
    public void setTag(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.indicator_dynamic_string_param.tag</code>.
     */
    public String getTag() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.indicator_dynamic_string_param.id</code>.
     */
    public void setId(UUID value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.indicator_dynamic_string_param.id</code>.
     */
    public UUID getId() {
        return (UUID) get(6);
    }

    /**
     * Setter for <code>public.indicator_dynamic_string_param.markup</code>.
     */
    public void setMarkup(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.indicator_dynamic_string_param.markup</code>.
     */
    public String getMarkup() {
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
        return IndicatorDynamicStringParam.INDICATOR_DYNAMIC_STRING_PARAM.CREATION_DATE;
    }

    @Override
    public Field<UUID> field2() {
        return IndicatorDynamicStringParam.INDICATOR_DYNAMIC_STRING_PARAM.CREATOR_ID;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return IndicatorDynamicStringParam.INDICATOR_DYNAMIC_STRING_PARAM.LAST_MODIFICATION_DATE;
    }

    @Override
    public Field<UUID> field4() {
        return IndicatorDynamicStringParam.INDICATOR_DYNAMIC_STRING_PARAM.LAST_MODIFIER_ID;
    }

    @Override
    public Field<UUID> field5() {
        return IndicatorDynamicStringParam.INDICATOR_DYNAMIC_STRING_PARAM.OWNER_ID;
    }

    @Override
    public Field<String> field6() {
        return IndicatorDynamicStringParam.INDICATOR_DYNAMIC_STRING_PARAM.TAG;
    }

    @Override
    public Field<UUID> field7() {
        return IndicatorDynamicStringParam.INDICATOR_DYNAMIC_STRING_PARAM.ID;
    }

    @Override
    public Field<String> field8() {
        return IndicatorDynamicStringParam.INDICATOR_DYNAMIC_STRING_PARAM.MARKUP;
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
        return getMarkup();
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
        return getMarkup();
    }

    @Override
    public IndicatorDynamicStringParamRecord value1(LocalDateTime value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public IndicatorDynamicStringParamRecord value2(UUID value) {
        setCreatorId(value);
        return this;
    }

    @Override
    public IndicatorDynamicStringParamRecord value3(LocalDateTime value) {
        setLastModificationDate(value);
        return this;
    }

    @Override
    public IndicatorDynamicStringParamRecord value4(UUID value) {
        setLastModifierId(value);
        return this;
    }

    @Override
    public IndicatorDynamicStringParamRecord value5(UUID value) {
        setOwnerId(value);
        return this;
    }

    @Override
    public IndicatorDynamicStringParamRecord value6(String value) {
        setTag(value);
        return this;
    }

    @Override
    public IndicatorDynamicStringParamRecord value7(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public IndicatorDynamicStringParamRecord value8(String value) {
        setMarkup(value);
        return this;
    }

    @Override
    public IndicatorDynamicStringParamRecord values(LocalDateTime value1, UUID value2, LocalDateTime value3, UUID value4, UUID value5, String value6, UUID value7, String value8) {
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
     * Create a detached IndicatorDynamicStringParamRecord
     */
    public IndicatorDynamicStringParamRecord() {
        super(IndicatorDynamicStringParam.INDICATOR_DYNAMIC_STRING_PARAM);
    }

    /**
     * Create a detached, initialised IndicatorDynamicStringParamRecord
     */
    public IndicatorDynamicStringParamRecord(LocalDateTime creationDate, UUID creatorId, LocalDateTime lastModificationDate, UUID lastModifierId, UUID ownerId, String tag, UUID id, String markup) {
        super(IndicatorDynamicStringParam.INDICATOR_DYNAMIC_STRING_PARAM);

        setCreationDate(creationDate);
        setCreatorId(creatorId);
        setLastModificationDate(lastModificationDate);
        setLastModifierId(lastModifierId);
        setOwnerId(ownerId);
        setTag(tag);
        setId(id);
        setMarkup(markup);
    }
}