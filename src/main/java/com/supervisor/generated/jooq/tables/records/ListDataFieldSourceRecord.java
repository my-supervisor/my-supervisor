/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables.records;


import com.supervisor.generated.jooq.tables.ListDataFieldSource;

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
public class ListDataFieldSourceRecord extends UpdatableRecordImpl<ListDataFieldSourceRecord> implements Record12<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, Boolean, UUID, UUID, UUID, UUID> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.list_data_field_source.creation_date</code>.
     */
    public void setCreationDate(LocalDateTime value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.list_data_field_source.creation_date</code>.
     */
    public LocalDateTime getCreationDate() {
        return (LocalDateTime) get(0);
    }

    /**
     * Setter for <code>public.list_data_field_source.creator_id</code>.
     */
    public void setCreatorId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.list_data_field_source.creator_id</code>.
     */
    public UUID getCreatorId() {
        return (UUID) get(1);
    }

    /**
     * Setter for
     * <code>public.list_data_field_source.last_modification_date</code>.
     */
    public void setLastModificationDate(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for
     * <code>public.list_data_field_source.last_modification_date</code>.
     */
    public LocalDateTime getLastModificationDate() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>public.list_data_field_source.last_modifier_id</code>.
     */
    public void setLastModifierId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.list_data_field_source.last_modifier_id</code>.
     */
    public UUID getLastModifierId() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>public.list_data_field_source.owner_id</code>.
     */
    public void setOwnerId(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.list_data_field_source.owner_id</code>.
     */
    public UUID getOwnerId() {
        return (UUID) get(4);
    }

    /**
     * Setter for <code>public.list_data_field_source.tag</code>.
     */
    public void setTag(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.list_data_field_source.tag</code>.
     */
    public String getTag() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.list_data_field_source.id</code>.
     */
    public void setId(UUID value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.list_data_field_source.id</code>.
     */
    public UUID getId() {
        return (UUID) get(6);
    }

    /**
     * Setter for <code>public.list_data_field_source.active</code>.
     */
    public void setActive(Boolean value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.list_data_field_source.active</code>.
     */
    public Boolean getActive() {
        return (Boolean) get(7);
    }

    /**
     * Setter for <code>public.list_data_field_source.model_id</code>.
     */
    public void setModelId(UUID value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.list_data_field_source.model_id</code>.
     */
    public UUID getModelId() {
        return (UUID) get(8);
    }

    /**
     * Setter for <code>public.list_data_field_source.order_field_id</code>.
     */
    public void setOrderFieldId(UUID value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.list_data_field_source.order_field_id</code>.
     */
    public UUID getOrderFieldId() {
        return (UUID) get(9);
    }

    /**
     * Setter for
     * <code>public.list_data_field_source.field_to_display_id</code>.
     */
    public void setFieldToDisplayId(UUID value) {
        set(10, value);
    }

    /**
     * Getter for
     * <code>public.list_data_field_source.field_to_display_id</code>.
     */
    public UUID getFieldToDisplayId() {
        return (UUID) get(10);
    }

    /**
     * Setter for <code>public.list_data_field_source.field_id</code>.
     */
    public void setFieldId(UUID value) {
        set(11, value);
    }

    /**
     * Getter for <code>public.list_data_field_source.field_id</code>.
     */
    public UUID getFieldId() {
        return (UUID) get(11);
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
    public Row12<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, Boolean, UUID, UUID, UUID, UUID> fieldsRow() {
        return (Row12) super.fieldsRow();
    }

    @Override
    public Row12<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, Boolean, UUID, UUID, UUID, UUID> valuesRow() {
        return (Row12) super.valuesRow();
    }

    @Override
    public Field<LocalDateTime> field1() {
        return ListDataFieldSource.LIST_DATA_FIELD_SOURCE.CREATION_DATE;
    }

    @Override
    public Field<UUID> field2() {
        return ListDataFieldSource.LIST_DATA_FIELD_SOURCE.CREATOR_ID;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return ListDataFieldSource.LIST_DATA_FIELD_SOURCE.LAST_MODIFICATION_DATE;
    }

    @Override
    public Field<UUID> field4() {
        return ListDataFieldSource.LIST_DATA_FIELD_SOURCE.LAST_MODIFIER_ID;
    }

    @Override
    public Field<UUID> field5() {
        return ListDataFieldSource.LIST_DATA_FIELD_SOURCE.OWNER_ID;
    }

    @Override
    public Field<String> field6() {
        return ListDataFieldSource.LIST_DATA_FIELD_SOURCE.TAG;
    }

    @Override
    public Field<UUID> field7() {
        return ListDataFieldSource.LIST_DATA_FIELD_SOURCE.ID;
    }

    @Override
    public Field<Boolean> field8() {
        return ListDataFieldSource.LIST_DATA_FIELD_SOURCE.ACTIVE;
    }

    @Override
    public Field<UUID> field9() {
        return ListDataFieldSource.LIST_DATA_FIELD_SOURCE.MODEL_ID;
    }

    @Override
    public Field<UUID> field10() {
        return ListDataFieldSource.LIST_DATA_FIELD_SOURCE.ORDER_FIELD_ID;
    }

    @Override
    public Field<UUID> field11() {
        return ListDataFieldSource.LIST_DATA_FIELD_SOURCE.FIELD_TO_DISPLAY_ID;
    }

    @Override
    public Field<UUID> field12() {
        return ListDataFieldSource.LIST_DATA_FIELD_SOURCE.FIELD_ID;
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
    public Boolean component8() {
        return getActive();
    }

    @Override
    public UUID component9() {
        return getModelId();
    }

    @Override
    public UUID component10() {
        return getOrderFieldId();
    }

    @Override
    public UUID component11() {
        return getFieldToDisplayId();
    }

    @Override
    public UUID component12() {
        return getFieldId();
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
    public Boolean value8() {
        return getActive();
    }

    @Override
    public UUID value9() {
        return getModelId();
    }

    @Override
    public UUID value10() {
        return getOrderFieldId();
    }

    @Override
    public UUID value11() {
        return getFieldToDisplayId();
    }

    @Override
    public UUID value12() {
        return getFieldId();
    }

    @Override
    public ListDataFieldSourceRecord value1(LocalDateTime value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public ListDataFieldSourceRecord value2(UUID value) {
        setCreatorId(value);
        return this;
    }

    @Override
    public ListDataFieldSourceRecord value3(LocalDateTime value) {
        setLastModificationDate(value);
        return this;
    }

    @Override
    public ListDataFieldSourceRecord value4(UUID value) {
        setLastModifierId(value);
        return this;
    }

    @Override
    public ListDataFieldSourceRecord value5(UUID value) {
        setOwnerId(value);
        return this;
    }

    @Override
    public ListDataFieldSourceRecord value6(String value) {
        setTag(value);
        return this;
    }

    @Override
    public ListDataFieldSourceRecord value7(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public ListDataFieldSourceRecord value8(Boolean value) {
        setActive(value);
        return this;
    }

    @Override
    public ListDataFieldSourceRecord value9(UUID value) {
        setModelId(value);
        return this;
    }

    @Override
    public ListDataFieldSourceRecord value10(UUID value) {
        setOrderFieldId(value);
        return this;
    }

    @Override
    public ListDataFieldSourceRecord value11(UUID value) {
        setFieldToDisplayId(value);
        return this;
    }

    @Override
    public ListDataFieldSourceRecord value12(UUID value) {
        setFieldId(value);
        return this;
    }

    @Override
    public ListDataFieldSourceRecord values(LocalDateTime value1, UUID value2, LocalDateTime value3, UUID value4, UUID value5, String value6, UUID value7, Boolean value8, UUID value9, UUID value10, UUID value11, UUID value12) {
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
     * Create a detached ListDataFieldSourceRecord
     */
    public ListDataFieldSourceRecord() {
        super(ListDataFieldSource.LIST_DATA_FIELD_SOURCE);
    }

    /**
     * Create a detached, initialised ListDataFieldSourceRecord
     */
    public ListDataFieldSourceRecord(LocalDateTime creationDate, UUID creatorId, LocalDateTime lastModificationDate, UUID lastModifierId, UUID ownerId, String tag, UUID id, Boolean active, UUID modelId, UUID orderFieldId, UUID fieldToDisplayId, UUID fieldId) {
        super(ListDataFieldSource.LIST_DATA_FIELD_SOURCE);

        setCreationDate(creationDate);
        setCreatorId(creatorId);
        setLastModificationDate(lastModificationDate);
        setLastModifierId(lastModifierId);
        setOwnerId(ownerId);
        setTag(tag);
        setId(id);
        setActive(active);
        setModelId(modelId);
        setOrderFieldId(orderFieldId);
        setFieldToDisplayId(fieldToDisplayId);
        setFieldId(fieldId);
    }
}
