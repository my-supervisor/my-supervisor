/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables.records;


import com.supervisor.generated.jooq.tables.FormularDataField;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class FormularDataFieldRecord extends UpdatableRecordImpl<FormularDataFieldRecord> implements Record7<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.formular_data_field.creation_date</code>.
     */
    public void setCreationDate(LocalDateTime value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.formular_data_field.creation_date</code>.
     */
    public LocalDateTime getCreationDate() {
        return (LocalDateTime) get(0);
    }

    /**
     * Setter for <code>public.formular_data_field.creator_id</code>.
     */
    public void setCreatorId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.formular_data_field.creator_id</code>.
     */
    public UUID getCreatorId() {
        return (UUID) get(1);
    }

    /**
     * Setter for
     * <code>public.formular_data_field.last_modification_date</code>.
     */
    public void setLastModificationDate(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for
     * <code>public.formular_data_field.last_modification_date</code>.
     */
    public LocalDateTime getLastModificationDate() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>public.formular_data_field.last_modifier_id</code>.
     */
    public void setLastModifierId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.formular_data_field.last_modifier_id</code>.
     */
    public UUID getLastModifierId() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>public.formular_data_field.owner_id</code>.
     */
    public void setOwnerId(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.formular_data_field.owner_id</code>.
     */
    public UUID getOwnerId() {
        return (UUID) get(4);
    }

    /**
     * Setter for <code>public.formular_data_field.tag</code>.
     */
    public void setTag(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.formular_data_field.tag</code>.
     */
    public String getTag() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.formular_data_field.id</code>.
     */
    public void setId(UUID value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.formular_data_field.id</code>.
     */
    public UUID getId() {
        return (UUID) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<LocalDateTime> field1() {
        return FormularDataField.FORMULAR_DATA_FIELD.CREATION_DATE;
    }

    @Override
    public Field<UUID> field2() {
        return FormularDataField.FORMULAR_DATA_FIELD.CREATOR_ID;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return FormularDataField.FORMULAR_DATA_FIELD.LAST_MODIFICATION_DATE;
    }

    @Override
    public Field<UUID> field4() {
        return FormularDataField.FORMULAR_DATA_FIELD.LAST_MODIFIER_ID;
    }

    @Override
    public Field<UUID> field5() {
        return FormularDataField.FORMULAR_DATA_FIELD.OWNER_ID;
    }

    @Override
    public Field<String> field6() {
        return FormularDataField.FORMULAR_DATA_FIELD.TAG;
    }

    @Override
    public Field<UUID> field7() {
        return FormularDataField.FORMULAR_DATA_FIELD.ID;
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
    public FormularDataFieldRecord value1(LocalDateTime value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public FormularDataFieldRecord value2(UUID value) {
        setCreatorId(value);
        return this;
    }

    @Override
    public FormularDataFieldRecord value3(LocalDateTime value) {
        setLastModificationDate(value);
        return this;
    }

    @Override
    public FormularDataFieldRecord value4(UUID value) {
        setLastModifierId(value);
        return this;
    }

    @Override
    public FormularDataFieldRecord value5(UUID value) {
        setOwnerId(value);
        return this;
    }

    @Override
    public FormularDataFieldRecord value6(String value) {
        setTag(value);
        return this;
    }

    @Override
    public FormularDataFieldRecord value7(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public FormularDataFieldRecord values(LocalDateTime value1, UUID value2, LocalDateTime value3, UUID value4, UUID value5, String value6, UUID value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached FormularDataFieldRecord
     */
    public FormularDataFieldRecord() {
        super(FormularDataField.FORMULAR_DATA_FIELD);
    }

    /**
     * Create a detached, initialised FormularDataFieldRecord
     */
    public FormularDataFieldRecord(LocalDateTime creationDate, UUID creatorId, LocalDateTime lastModificationDate, UUID lastModifierId, UUID ownerId, String tag, UUID id) {
        super(FormularDataField.FORMULAR_DATA_FIELD);

        setCreationDate(creationDate);
        setCreatorId(creatorId);
        setLastModificationDate(lastModificationDate);
        setLastModifierId(lastModifierId);
        setOwnerId(ownerId);
        setTag(tag);
        setId(id);
    }
}
