/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables.records;


import com.supervisor.generated.jooq.tables.DataModel;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record15;
import org.jooq.Row15;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DataModelRecord extends UpdatableRecordImpl<DataModelRecord> implements Record15<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, Boolean, Boolean, UUID, String, Boolean, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.data_model.creation_date</code>.
     */
    public void setCreationDate(LocalDateTime value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.data_model.creation_date</code>.
     */
    public LocalDateTime getCreationDate() {
        return (LocalDateTime) get(0);
    }

    /**
     * Setter for <code>public.data_model.creator_id</code>.
     */
    public void setCreatorId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.data_model.creator_id</code>.
     */
    public UUID getCreatorId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>public.data_model.last_modification_date</code>.
     */
    public void setLastModificationDate(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.data_model.last_modification_date</code>.
     */
    public LocalDateTime getLastModificationDate() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>public.data_model.last_modifier_id</code>.
     */
    public void setLastModifierId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.data_model.last_modifier_id</code>.
     */
    public UUID getLastModifierId() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>public.data_model.owner_id</code>.
     */
    public void setOwnerId(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.data_model.owner_id</code>.
     */
    public UUID getOwnerId() {
        return (UUID) get(4);
    }

    /**
     * Setter for <code>public.data_model.tag</code>.
     */
    public void setTag(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.data_model.tag</code>.
     */
    public String getTag() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.data_model.id</code>.
     */
    public void setId(UUID value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.data_model.id</code>.
     */
    public UUID getId() {
        return (UUID) get(6);
    }

    /**
     * Setter for <code>public.data_model.description</code>.
     */
    public void setDescription(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.data_model.description</code>.
     */
    public String getDescription() {
        return (String) get(7);
    }

    /**
     * Setter for <code>public.data_model.is_template</code>.
     */
    public void setIsTemplate(Boolean value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.data_model.is_template</code>.
     */
    public Boolean getIsTemplate() {
        return (Boolean) get(8);
    }

    /**
     * Setter for <code>public.data_model.active</code>.
     */
    public void setActive(Boolean value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.data_model.active</code>.
     */
    public Boolean getActive() {
        return (Boolean) get(9);
    }

    /**
     * Setter for <code>public.data_model.activity_id</code>.
     */
    public void setActivityId(UUID value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.data_model.activity_id</code>.
     */
    public UUID getActivityId() {
        return (UUID) get(10);
    }

    /**
     * Setter for <code>public.data_model.name</code>.
     */
    public void setName(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>public.data_model.name</code>.
     */
    public String getName() {
        return (String) get(11);
    }

    /**
     * Setter for <code>public.data_model.interacting</code>.
     */
    public void setInteracting(Boolean value) {
        set(12, value);
    }

    /**
     * Getter for <code>public.data_model.interacting</code>.
     */
    public Boolean getInteracting() {
        return (Boolean) get(12);
    }

    /**
     * Setter for <code>public.data_model.type</code>.
     */
    public void setType(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>public.data_model.type</code>.
     */
    public String getType() {
        return (String) get(13);
    }

    /**
     * Setter for <code>public.data_model.code</code>.
     */
    public void setCode(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>public.data_model.code</code>.
     */
    public String getCode() {
        return (String) get(14);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record15 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row15<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, Boolean, Boolean, UUID, String, Boolean, String, String> fieldsRow() {
        return (Row15) super.fieldsRow();
    }

    @Override
    public Row15<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, Boolean, Boolean, UUID, String, Boolean, String, String> valuesRow() {
        return (Row15) super.valuesRow();
    }

    @Override
    public Field<LocalDateTime> field1() {
        return DataModel.DATA_MODEL.CREATION_DATE;
    }

    @Override
    public Field<UUID> field2() {
        return DataModel.DATA_MODEL.CREATOR_ID;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return DataModel.DATA_MODEL.LAST_MODIFICATION_DATE;
    }

    @Override
    public Field<UUID> field4() {
        return DataModel.DATA_MODEL.LAST_MODIFIER_ID;
    }

    @Override
    public Field<UUID> field5() {
        return DataModel.DATA_MODEL.OWNER_ID;
    }

    @Override
    public Field<String> field6() {
        return DataModel.DATA_MODEL.TAG;
    }

    @Override
    public Field<UUID> field7() {
        return DataModel.DATA_MODEL.ID;
    }

    @Override
    public Field<String> field8() {
        return DataModel.DATA_MODEL.DESCRIPTION;
    }

    @Override
    public Field<Boolean> field9() {
        return DataModel.DATA_MODEL.IS_TEMPLATE;
    }

    @Override
    public Field<Boolean> field10() {
        return DataModel.DATA_MODEL.ACTIVE;
    }

    @Override
    public Field<UUID> field11() {
        return DataModel.DATA_MODEL.ACTIVITY_ID;
    }

    @Override
    public Field<String> field12() {
        return DataModel.DATA_MODEL.NAME;
    }

    @Override
    public Field<Boolean> field13() {
        return DataModel.DATA_MODEL.INTERACTING;
    }

    @Override
    public Field<String> field14() {
        return DataModel.DATA_MODEL.TYPE;
    }

    @Override
    public Field<String> field15() {
        return DataModel.DATA_MODEL.CODE;
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
    public Boolean component9() {
        return getIsTemplate();
    }

    @Override
    public Boolean component10() {
        return getActive();
    }

    @Override
    public UUID component11() {
        return getActivityId();
    }

    @Override
    public String component12() {
        return getName();
    }

    @Override
    public Boolean component13() {
        return getInteracting();
    }

    @Override
    public String component14() {
        return getType();
    }

    @Override
    public String component15() {
        return getCode();
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
    public Boolean value9() {
        return getIsTemplate();
    }

    @Override
    public Boolean value10() {
        return getActive();
    }

    @Override
    public UUID value11() {
        return getActivityId();
    }

    @Override
    public String value12() {
        return getName();
    }

    @Override
    public Boolean value13() {
        return getInteracting();
    }

    @Override
    public String value14() {
        return getType();
    }

    @Override
    public String value15() {
        return getCode();
    }

    @Override
    public DataModelRecord value1(LocalDateTime value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public DataModelRecord value2(UUID value) {
        setCreatorId(value);
        return this;
    }

    @Override
    public DataModelRecord value3(LocalDateTime value) {
        setLastModificationDate(value);
        return this;
    }

    @Override
    public DataModelRecord value4(UUID value) {
        setLastModifierId(value);
        return this;
    }

    @Override
    public DataModelRecord value5(UUID value) {
        setOwnerId(value);
        return this;
    }

    @Override
    public DataModelRecord value6(String value) {
        setTag(value);
        return this;
    }

    @Override
    public DataModelRecord value7(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public DataModelRecord value8(String value) {
        setDescription(value);
        return this;
    }

    @Override
    public DataModelRecord value9(Boolean value) {
        setIsTemplate(value);
        return this;
    }

    @Override
    public DataModelRecord value10(Boolean value) {
        setActive(value);
        return this;
    }

    @Override
    public DataModelRecord value11(UUID value) {
        setActivityId(value);
        return this;
    }

    @Override
    public DataModelRecord value12(String value) {
        setName(value);
        return this;
    }

    @Override
    public DataModelRecord value13(Boolean value) {
        setInteracting(value);
        return this;
    }

    @Override
    public DataModelRecord value14(String value) {
        setType(value);
        return this;
    }

    @Override
    public DataModelRecord value15(String value) {
        setCode(value);
        return this;
    }

    @Override
    public DataModelRecord values(LocalDateTime value1, UUID value2, LocalDateTime value3, UUID value4, UUID value5, String value6, UUID value7, String value8, Boolean value9, Boolean value10, UUID value11, String value12, Boolean value13, String value14, String value15) {
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
        value15(value15);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DataModelRecord
     */
    public DataModelRecord() {
        super(DataModel.DATA_MODEL);
    }

    /**
     * Create a detached, initialised DataModelRecord
     */
    public DataModelRecord(LocalDateTime creationDate, UUID creatorId, LocalDateTime lastModificationDate, UUID lastModifierId, UUID ownerId, String tag, UUID id, String description, Boolean isTemplate, Boolean active, UUID activityId, String name, Boolean interacting, String type, String code) {
        super(DataModel.DATA_MODEL);

        setCreationDate(creationDate);
        setCreatorId(creatorId);
        setLastModificationDate(lastModificationDate);
        setLastModifierId(lastModifierId);
        setOwnerId(ownerId);
        setTag(tag);
        setId(id);
        setDescription(description);
        setIsTemplate(isTemplate);
        setActive(active);
        setActivityId(activityId);
        setName(name);
        setInteracting(interacting);
        setType(type);
        setCode(code);
    }
}
