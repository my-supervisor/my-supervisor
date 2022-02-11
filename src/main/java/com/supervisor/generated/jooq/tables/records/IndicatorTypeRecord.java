/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables.records;


import com.supervisor.generated.jooq.tables.IndicatorType;

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
public class IndicatorTypeRecord extends UpdatableRecordImpl<IndicatorTypeRecord> implements Record12<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String, Integer, Integer, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.indicator_type.creation_date</code>.
     */
    public void setCreationDate(LocalDateTime value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.indicator_type.creation_date</code>.
     */
    public LocalDateTime getCreationDate() {
        return (LocalDateTime) get(0);
    }

    /**
     * Setter for <code>public.indicator_type.creator_id</code>.
     */
    public void setCreatorId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.indicator_type.creator_id</code>.
     */
    public UUID getCreatorId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>public.indicator_type.last_modification_date</code>.
     */
    public void setLastModificationDate(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.indicator_type.last_modification_date</code>.
     */
    public LocalDateTime getLastModificationDate() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>public.indicator_type.last_modifier_id</code>.
     */
    public void setLastModifierId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.indicator_type.last_modifier_id</code>.
     */
    public UUID getLastModifierId() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>public.indicator_type.owner_id</code>.
     */
    public void setOwnerId(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.indicator_type.owner_id</code>.
     */
    public UUID getOwnerId() {
        return (UUID) get(4);
    }

    /**
     * Setter for <code>public.indicator_type.tag</code>.
     */
    public void setTag(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.indicator_type.tag</code>.
     */
    public String getTag() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.indicator_type.id</code>.
     */
    public void setId(UUID value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.indicator_type.id</code>.
     */
    public UUID getId() {
        return (UUID) get(6);
    }

    /**
     * Setter for <code>public.indicator_type.description</code>.
     */
    public void setDescription(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.indicator_type.description</code>.
     */
    public String getDescription() {
        return (String) get(7);
    }

    /**
     * Setter for <code>public.indicator_type.name</code>.
     */
    public void setName(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.indicator_type.name</code>.
     */
    public String getName() {
        return (String) get(8);
    }

    /**
     * Setter for <code>public.indicator_type.default_size_x</code>.
     */
    public void setDefaultSizeX(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.indicator_type.default_size_x</code>.
     */
    public Integer getDefaultSizeX() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>public.indicator_type.default_size_y</code>.
     */
    public void setDefaultSizeY(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.indicator_type.default_size_y</code>.
     */
    public Integer getDefaultSizeY() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>public.indicator_type.short_name</code>.
     */
    public void setShortName(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>public.indicator_type.short_name</code>.
     */
    public String getShortName() {
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
    public Row12<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String, Integer, Integer, String> fieldsRow() {
        return (Row12) super.fieldsRow();
    }

    @Override
    public Row12<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String, Integer, Integer, String> valuesRow() {
        return (Row12) super.valuesRow();
    }

    @Override
    public Field<LocalDateTime> field1() {
        return IndicatorType.INDICATOR_TYPE.CREATION_DATE;
    }

    @Override
    public Field<UUID> field2() {
        return IndicatorType.INDICATOR_TYPE.CREATOR_ID;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return IndicatorType.INDICATOR_TYPE.LAST_MODIFICATION_DATE;
    }

    @Override
    public Field<UUID> field4() {
        return IndicatorType.INDICATOR_TYPE.LAST_MODIFIER_ID;
    }

    @Override
    public Field<UUID> field5() {
        return IndicatorType.INDICATOR_TYPE.OWNER_ID;
    }

    @Override
    public Field<String> field6() {
        return IndicatorType.INDICATOR_TYPE.TAG;
    }

    @Override
    public Field<UUID> field7() {
        return IndicatorType.INDICATOR_TYPE.ID;
    }

    @Override
    public Field<String> field8() {
        return IndicatorType.INDICATOR_TYPE.DESCRIPTION;
    }

    @Override
    public Field<String> field9() {
        return IndicatorType.INDICATOR_TYPE.NAME;
    }

    @Override
    public Field<Integer> field10() {
        return IndicatorType.INDICATOR_TYPE.DEFAULT_SIZE_X;
    }

    @Override
    public Field<Integer> field11() {
        return IndicatorType.INDICATOR_TYPE.DEFAULT_SIZE_Y;
    }

    @Override
    public Field<String> field12() {
        return IndicatorType.INDICATOR_TYPE.SHORT_NAME;
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
        return getDescription();
    }

    @Override
    public String component9() {
        return getName();
    }

    @Override
    public Integer component10() {
        return getDefaultSizeX();
    }

    @Override
    public Integer component11() {
        return getDefaultSizeY();
    }

    @Override
    public String component12() {
        return getShortName();
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
        return getDescription();
    }

    @Override
    public String value9() {
        return getName();
    }

    @Override
    public Integer value10() {
        return getDefaultSizeX();
    }

    @Override
    public Integer value11() {
        return getDefaultSizeY();
    }

    @Override
    public String value12() {
        return getShortName();
    }

    @Override
    public IndicatorTypeRecord value1(LocalDateTime value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public IndicatorTypeRecord value2(UUID value) {
        setCreatorId(value);
        return this;
    }

    @Override
    public IndicatorTypeRecord value3(LocalDateTime value) {
        setLastModificationDate(value);
        return this;
    }

    @Override
    public IndicatorTypeRecord value4(UUID value) {
        setLastModifierId(value);
        return this;
    }

    @Override
    public IndicatorTypeRecord value5(UUID value) {
        setOwnerId(value);
        return this;
    }

    @Override
    public IndicatorTypeRecord value6(String value) {
        setTag(value);
        return this;
    }

    @Override
    public IndicatorTypeRecord value7(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public IndicatorTypeRecord value8(String value) {
        setDescription(value);
        return this;
    }

    @Override
    public IndicatorTypeRecord value9(String value) {
        setName(value);
        return this;
    }

    @Override
    public IndicatorTypeRecord value10(Integer value) {
        setDefaultSizeX(value);
        return this;
    }

    @Override
    public IndicatorTypeRecord value11(Integer value) {
        setDefaultSizeY(value);
        return this;
    }

    @Override
    public IndicatorTypeRecord value12(String value) {
        setShortName(value);
        return this;
    }

    @Override
    public IndicatorTypeRecord values(LocalDateTime value1, UUID value2, LocalDateTime value3, UUID value4, UUID value5, String value6, UUID value7, String value8, String value9, Integer value10, Integer value11, String value12) {
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
     * Create a detached IndicatorTypeRecord
     */
    public IndicatorTypeRecord() {
        super(IndicatorType.INDICATOR_TYPE);
    }

    /**
     * Create a detached, initialised IndicatorTypeRecord
     */
    public IndicatorTypeRecord(LocalDateTime creationDate, UUID creatorId, LocalDateTime lastModificationDate, UUID lastModifierId, UUID ownerId, String tag, UUID id, String description, String name, Integer defaultSizeX, Integer defaultSizeY, String shortName) {
        super(IndicatorType.INDICATOR_TYPE);

        setCreationDate(creationDate);
        setCreatorId(creatorId);
        setLastModificationDate(lastModificationDate);
        setLastModifierId(lastModifierId);
        setOwnerId(ownerId);
        setTag(tag);
        setId(id);
        setDescription(description);
        setName(name);
        setDefaultSizeX(defaultSizeX);
        setDefaultSizeY(defaultSizeY);
        setShortName(shortName);
    }
}