/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables.records;


import com.supervisor.generated.jooq.tables.IndicatorTypeParam;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record14;
import org.jooq.Row14;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class IndicatorTypeParamRecord extends UpdatableRecordImpl<IndicatorTypeParamRecord> implements Record14<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String, Integer, String, String, UUID, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.indicator_type_param.creation_date</code>.
     */
    public void setCreationDate(LocalDateTime value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.indicator_type_param.creation_date</code>.
     */
    public LocalDateTime getCreationDate() {
        return (LocalDateTime) get(0);
    }

    /**
     * Setter for <code>public.indicator_type_param.creator_id</code>.
     */
    public void setCreatorId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.indicator_type_param.creator_id</code>.
     */
    public UUID getCreatorId() {
        return (UUID) get(1);
    }

    /**
     * Setter for
     * <code>public.indicator_type_param.last_modification_date</code>.
     */
    public void setLastModificationDate(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for
     * <code>public.indicator_type_param.last_modification_date</code>.
     */
    public LocalDateTime getLastModificationDate() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>public.indicator_type_param.last_modifier_id</code>.
     */
    public void setLastModifierId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.indicator_type_param.last_modifier_id</code>.
     */
    public UUID getLastModifierId() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>public.indicator_type_param.owner_id</code>.
     */
    public void setOwnerId(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.indicator_type_param.owner_id</code>.
     */
    public UUID getOwnerId() {
        return (UUID) get(4);
    }

    /**
     * Setter for <code>public.indicator_type_param.tag</code>.
     */
    public void setTag(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.indicator_type_param.tag</code>.
     */
    public String getTag() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.indicator_type_param.id</code>.
     */
    public void setId(UUID value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.indicator_type_param.id</code>.
     */
    public UUID getId() {
        return (UUID) get(6);
    }

    /**
     * Setter for <code>public.indicator_type_param.code</code>.
     */
    public void setCode(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.indicator_type_param.code</code>.
     */
    public String getCode() {
        return (String) get(7);
    }

    /**
     * Setter for <code>public.indicator_type_param.type</code>.
     */
    public void setType(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.indicator_type_param.type</code>.
     */
    public String getType() {
        return (String) get(8);
    }

    /**
     * Setter for <code>public.indicator_type_param.no</code>.
     */
    public void setNo(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.indicator_type_param.no</code>.
     */
    public Integer getNo() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>public.indicator_type_param.category</code>.
     */
    public void setCategory(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.indicator_type_param.category</code>.
     */
    public String getCategory() {
        return (String) get(10);
    }

    /**
     * Setter for <code>public.indicator_type_param.name</code>.
     */
    public void setName(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>public.indicator_type_param.name</code>.
     */
    public String getName() {
        return (String) get(11);
    }

    /**
     * Setter for <code>public.indicator_type_param.indicator_type_id</code>.
     */
    public void setIndicatorTypeId(UUID value) {
        set(12, value);
    }

    /**
     * Getter for <code>public.indicator_type_param.indicator_type_id</code>.
     */
    public UUID getIndicatorTypeId() {
        return (UUID) get(12);
    }

    /**
     * Setter for <code>public.indicator_type_param.predefined_values</code>.
     */
    public void setPredefinedValues(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>public.indicator_type_param.predefined_values</code>.
     */
    public String getPredefinedValues() {
        return (String) get(13);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record14 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row14<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String, Integer, String, String, UUID, String> fieldsRow() {
        return (Row14) super.fieldsRow();
    }

    @Override
    public Row14<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String, Integer, String, String, UUID, String> valuesRow() {
        return (Row14) super.valuesRow();
    }

    @Override
    public Field<LocalDateTime> field1() {
        return IndicatorTypeParam.INDICATOR_TYPE_PARAM.CREATION_DATE;
    }

    @Override
    public Field<UUID> field2() {
        return IndicatorTypeParam.INDICATOR_TYPE_PARAM.CREATOR_ID;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return IndicatorTypeParam.INDICATOR_TYPE_PARAM.LAST_MODIFICATION_DATE;
    }

    @Override
    public Field<UUID> field4() {
        return IndicatorTypeParam.INDICATOR_TYPE_PARAM.LAST_MODIFIER_ID;
    }

    @Override
    public Field<UUID> field5() {
        return IndicatorTypeParam.INDICATOR_TYPE_PARAM.OWNER_ID;
    }

    @Override
    public Field<String> field6() {
        return IndicatorTypeParam.INDICATOR_TYPE_PARAM.TAG;
    }

    @Override
    public Field<UUID> field7() {
        return IndicatorTypeParam.INDICATOR_TYPE_PARAM.ID;
    }

    @Override
    public Field<String> field8() {
        return IndicatorTypeParam.INDICATOR_TYPE_PARAM.CODE;
    }

    @Override
    public Field<String> field9() {
        return IndicatorTypeParam.INDICATOR_TYPE_PARAM.TYPE;
    }

    @Override
    public Field<Integer> field10() {
        return IndicatorTypeParam.INDICATOR_TYPE_PARAM.NO;
    }

    @Override
    public Field<String> field11() {
        return IndicatorTypeParam.INDICATOR_TYPE_PARAM.CATEGORY;
    }

    @Override
    public Field<String> field12() {
        return IndicatorTypeParam.INDICATOR_TYPE_PARAM.NAME;
    }

    @Override
    public Field<UUID> field13() {
        return IndicatorTypeParam.INDICATOR_TYPE_PARAM.INDICATOR_TYPE_ID;
    }

    @Override
    public Field<String> field14() {
        return IndicatorTypeParam.INDICATOR_TYPE_PARAM.PREDEFINED_VALUES;
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
        return getCode();
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
    public String component11() {
        return getCategory();
    }

    @Override
    public String component12() {
        return getName();
    }

    @Override
    public UUID component13() {
        return getIndicatorTypeId();
    }

    @Override
    public String component14() {
        return getPredefinedValues();
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
        return getCode();
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
    public String value11() {
        return getCategory();
    }

    @Override
    public String value12() {
        return getName();
    }

    @Override
    public UUID value13() {
        return getIndicatorTypeId();
    }

    @Override
    public String value14() {
        return getPredefinedValues();
    }

    @Override
    public IndicatorTypeParamRecord value1(LocalDateTime value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public IndicatorTypeParamRecord value2(UUID value) {
        setCreatorId(value);
        return this;
    }

    @Override
    public IndicatorTypeParamRecord value3(LocalDateTime value) {
        setLastModificationDate(value);
        return this;
    }

    @Override
    public IndicatorTypeParamRecord value4(UUID value) {
        setLastModifierId(value);
        return this;
    }

    @Override
    public IndicatorTypeParamRecord value5(UUID value) {
        setOwnerId(value);
        return this;
    }

    @Override
    public IndicatorTypeParamRecord value6(String value) {
        setTag(value);
        return this;
    }

    @Override
    public IndicatorTypeParamRecord value7(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public IndicatorTypeParamRecord value8(String value) {
        setCode(value);
        return this;
    }

    @Override
    public IndicatorTypeParamRecord value9(String value) {
        setType(value);
        return this;
    }

    @Override
    public IndicatorTypeParamRecord value10(Integer value) {
        setNo(value);
        return this;
    }

    @Override
    public IndicatorTypeParamRecord value11(String value) {
        setCategory(value);
        return this;
    }

    @Override
    public IndicatorTypeParamRecord value12(String value) {
        setName(value);
        return this;
    }

    @Override
    public IndicatorTypeParamRecord value13(UUID value) {
        setIndicatorTypeId(value);
        return this;
    }

    @Override
    public IndicatorTypeParamRecord value14(String value) {
        setPredefinedValues(value);
        return this;
    }

    @Override
    public IndicatorTypeParamRecord values(LocalDateTime value1, UUID value2, LocalDateTime value3, UUID value4, UUID value5, String value6, UUID value7, String value8, String value9, Integer value10, String value11, String value12, UUID value13, String value14) {
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
        value13(value13);
        value14(value14);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached IndicatorTypeParamRecord
     */
    public IndicatorTypeParamRecord() {
        super(IndicatorTypeParam.INDICATOR_TYPE_PARAM);
    }

    /**
     * Create a detached, initialised IndicatorTypeParamRecord
     */
    public IndicatorTypeParamRecord(LocalDateTime creationDate, UUID creatorId, LocalDateTime lastModificationDate, UUID lastModifierId, UUID ownerId, String tag, UUID id, String code, String type, Integer no, String category, String name, UUID indicatorTypeId, String predefinedValues) {
        super(IndicatorTypeParam.INDICATOR_TYPE_PARAM);

        setCreationDate(creationDate);
        setCreatorId(creatorId);
        setLastModificationDate(lastModificationDate);
        setLastModifierId(lastModifierId);
        setOwnerId(ownerId);
        setTag(tag);
        setId(id);
        setCode(code);
        setType(type);
        setNo(no);
        setCategory(category);
        setName(name);
        setIndicatorTypeId(indicatorTypeId);
        setPredefinedValues(predefinedValues);
    }
}