/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables.records;


import com.supervisor.generated.jooq.tables.DataLink;

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
public class DataLinkRecord extends UpdatableRecordImpl<DataLinkRecord> implements Record12<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String, UUID, Boolean, UUID> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.data_link.creation_date</code>.
     */
    public void setCreationDate(LocalDateTime value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.data_link.creation_date</code>.
     */
    public LocalDateTime getCreationDate() {
        return (LocalDateTime) get(0);
    }

    /**
     * Setter for <code>public.data_link.creator_id</code>.
     */
    public void setCreatorId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.data_link.creator_id</code>.
     */
    public UUID getCreatorId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>public.data_link.last_modification_date</code>.
     */
    public void setLastModificationDate(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.data_link.last_modification_date</code>.
     */
    public LocalDateTime getLastModificationDate() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>public.data_link.last_modifier_id</code>.
     */
    public void setLastModifierId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.data_link.last_modifier_id</code>.
     */
    public UUID getLastModifierId() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>public.data_link.owner_id</code>.
     */
    public void setOwnerId(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.data_link.owner_id</code>.
     */
    public UUID getOwnerId() {
        return (UUID) get(4);
    }

    /**
     * Setter for <code>public.data_link.tag</code>.
     */
    public void setTag(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.data_link.tag</code>.
     */
    public String getTag() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.data_link.id</code>.
     */
    public void setId(UUID value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.data_link.id</code>.
     */
    public UUID getId() {
        return (UUID) get(6);
    }

    /**
     * Setter for <code>public.data_link.name</code>.
     */
    public void setName(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.data_link.name</code>.
     */
    public String getName() {
        return (String) get(7);
    }

    /**
     * Setter for <code>public.data_link.data_domain_definition</code>.
     */
    public void setDataDomainDefinition(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.data_link.data_domain_definition</code>.
     */
    public String getDataDomainDefinition() {
        return (String) get(8);
    }

    /**
     * Setter for <code>public.data_link.indicator_id</code>.
     */
    public void setIndicatorId(UUID value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.data_link.indicator_id</code>.
     */
    public UUID getIndicatorId() {
        return (UUID) get(9);
    }

    /**
     * Setter for <code>public.data_link.active</code>.
     */
    public void setActive(Boolean value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.data_link.active</code>.
     */
    public Boolean getActive() {
        return (Boolean) get(10);
    }

    /**
     * Setter for <code>public.data_link.model_id</code>.
     */
    public void setModelId(UUID value) {
        set(11, value);
    }

    /**
     * Getter for <code>public.data_link.model_id</code>.
     */
    public UUID getModelId() {
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
    public Row12<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String, UUID, Boolean, UUID> fieldsRow() {
        return (Row12) super.fieldsRow();
    }

    @Override
    public Row12<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String, UUID, Boolean, UUID> valuesRow() {
        return (Row12) super.valuesRow();
    }

    @Override
    public Field<LocalDateTime> field1() {
        return DataLink.DATA_LINK.CREATION_DATE;
    }

    @Override
    public Field<UUID> field2() {
        return DataLink.DATA_LINK.CREATOR_ID;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return DataLink.DATA_LINK.LAST_MODIFICATION_DATE;
    }

    @Override
    public Field<UUID> field4() {
        return DataLink.DATA_LINK.LAST_MODIFIER_ID;
    }

    @Override
    public Field<UUID> field5() {
        return DataLink.DATA_LINK.OWNER_ID;
    }

    @Override
    public Field<String> field6() {
        return DataLink.DATA_LINK.TAG;
    }

    @Override
    public Field<UUID> field7() {
        return DataLink.DATA_LINK.ID;
    }

    @Override
    public Field<String> field8() {
        return DataLink.DATA_LINK.NAME;
    }

    @Override
    public Field<String> field9() {
        return DataLink.DATA_LINK.DATA_DOMAIN_DEFINITION;
    }

    @Override
    public Field<UUID> field10() {
        return DataLink.DATA_LINK.INDICATOR_ID;
    }

    @Override
    public Field<Boolean> field11() {
        return DataLink.DATA_LINK.ACTIVE;
    }

    @Override
    public Field<UUID> field12() {
        return DataLink.DATA_LINK.MODEL_ID;
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
        return getName();
    }

    @Override
    public String component9() {
        return getDataDomainDefinition();
    }

    @Override
    public UUID component10() {
        return getIndicatorId();
    }

    @Override
    public Boolean component11() {
        return getActive();
    }

    @Override
    public UUID component12() {
        return getModelId();
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
        return getName();
    }

    @Override
    public String value9() {
        return getDataDomainDefinition();
    }

    @Override
    public UUID value10() {
        return getIndicatorId();
    }

    @Override
    public Boolean value11() {
        return getActive();
    }

    @Override
    public UUID value12() {
        return getModelId();
    }

    @Override
    public DataLinkRecord value1(LocalDateTime value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public DataLinkRecord value2(UUID value) {
        setCreatorId(value);
        return this;
    }

    @Override
    public DataLinkRecord value3(LocalDateTime value) {
        setLastModificationDate(value);
        return this;
    }

    @Override
    public DataLinkRecord value4(UUID value) {
        setLastModifierId(value);
        return this;
    }

    @Override
    public DataLinkRecord value5(UUID value) {
        setOwnerId(value);
        return this;
    }

    @Override
    public DataLinkRecord value6(String value) {
        setTag(value);
        return this;
    }

    @Override
    public DataLinkRecord value7(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public DataLinkRecord value8(String value) {
        setName(value);
        return this;
    }

    @Override
    public DataLinkRecord value9(String value) {
        setDataDomainDefinition(value);
        return this;
    }

    @Override
    public DataLinkRecord value10(UUID value) {
        setIndicatorId(value);
        return this;
    }

    @Override
    public DataLinkRecord value11(Boolean value) {
        setActive(value);
        return this;
    }

    @Override
    public DataLinkRecord value12(UUID value) {
        setModelId(value);
        return this;
    }

    @Override
    public DataLinkRecord values(LocalDateTime value1, UUID value2, LocalDateTime value3, UUID value4, UUID value5, String value6, UUID value7, String value8, String value9, UUID value10, Boolean value11, UUID value12) {
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
     * Create a detached DataLinkRecord
     */
    public DataLinkRecord() {
        super(DataLink.DATA_LINK);
    }

    /**
     * Create a detached, initialised DataLinkRecord
     */
    public DataLinkRecord(LocalDateTime creationDate, UUID creatorId, LocalDateTime lastModificationDate, UUID lastModifierId, UUID ownerId, String tag, UUID id, String name, String dataDomainDefinition, UUID indicatorId, Boolean active, UUID modelId) {
        super(DataLink.DATA_LINK);

        setCreationDate(creationDate);
        setCreatorId(creatorId);
        setLastModificationDate(lastModificationDate);
        setLastModifierId(lastModifierId);
        setOwnerId(ownerId);
        setTag(tag);
        setId(id);
        setName(name);
        setDataDomainDefinition(dataDomainDefinition);
        setIndicatorId(indicatorId);
        setActive(active);
        setModelId(modelId);
    }
}
