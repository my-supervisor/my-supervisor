/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables.records;


import com.supervisor.generated.jooq.tables.Access;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AccessRecord extends UpdatableRecordImpl<AccessRecord> implements Record9<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.access.creation_date</code>.
     */
    public void setCreationDate(LocalDateTime value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.access.creation_date</code>.
     */
    public LocalDateTime getCreationDate() {
        return (LocalDateTime) get(0);
    }

    /**
     * Setter for <code>public.access.creator_id</code>.
     */
    public void setCreatorId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.access.creator_id</code>.
     */
    public UUID getCreatorId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>public.access.last_modification_date</code>.
     */
    public void setLastModificationDate(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.access.last_modification_date</code>.
     */
    public LocalDateTime getLastModificationDate() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>public.access.last_modifier_id</code>.
     */
    public void setLastModifierId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.access.last_modifier_id</code>.
     */
    public UUID getLastModifierId() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>public.access.owner_id</code>.
     */
    public void setOwnerId(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.access.owner_id</code>.
     */
    public UUID getOwnerId() {
        return (UUID) get(4);
    }

    /**
     * Setter for <code>public.access.tag</code>.
     */
    public void setTag(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.access.tag</code>.
     */
    public String getTag() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.access.id</code>.
     */
    public void setId(UUID value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.access.id</code>.
     */
    public UUID getId() {
        return (UUID) get(6);
    }

    /**
     * Setter for <code>public.access.name</code>.
     */
    public void setName(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.access.name</code>.
     */
    public String getName() {
        return (String) get(7);
    }

    /**
     * Setter for <code>public.access.code</code>.
     */
    public void setCode(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.access.code</code>.
     */
    public String getCode() {
        return (String) get(8);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row9<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    @Override
    public Row9<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String> valuesRow() {
        return (Row9) super.valuesRow();
    }

    @Override
    public Field<LocalDateTime> field1() {
        return Access.ACCESS.CREATION_DATE;
    }

    @Override
    public Field<UUID> field2() {
        return Access.ACCESS.CREATOR_ID;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return Access.ACCESS.LAST_MODIFICATION_DATE;
    }

    @Override
    public Field<UUID> field4() {
        return Access.ACCESS.LAST_MODIFIER_ID;
    }

    @Override
    public Field<UUID> field5() {
        return Access.ACCESS.OWNER_ID;
    }

    @Override
    public Field<String> field6() {
        return Access.ACCESS.TAG;
    }

    @Override
    public Field<UUID> field7() {
        return Access.ACCESS.ID;
    }

    @Override
    public Field<String> field8() {
        return Access.ACCESS.NAME;
    }

    @Override
    public Field<String> field9() {
        return Access.ACCESS.CODE;
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
        return getName();
    }

    @Override
    public String value9() {
        return getCode();
    }

    @Override
    public AccessRecord value1(LocalDateTime value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public AccessRecord value2(UUID value) {
        setCreatorId(value);
        return this;
    }

    @Override
    public AccessRecord value3(LocalDateTime value) {
        setLastModificationDate(value);
        return this;
    }

    @Override
    public AccessRecord value4(UUID value) {
        setLastModifierId(value);
        return this;
    }

    @Override
    public AccessRecord value5(UUID value) {
        setOwnerId(value);
        return this;
    }

    @Override
    public AccessRecord value6(String value) {
        setTag(value);
        return this;
    }

    @Override
    public AccessRecord value7(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public AccessRecord value8(String value) {
        setName(value);
        return this;
    }

    @Override
    public AccessRecord value9(String value) {
        setCode(value);
        return this;
    }

    @Override
    public AccessRecord values(LocalDateTime value1, UUID value2, LocalDateTime value3, UUID value4, UUID value5, String value6, UUID value7, String value8, String value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AccessRecord
     */
    public AccessRecord() {
        super(Access.ACCESS);
    }

    /**
     * Create a detached, initialised AccessRecord
     */
    public AccessRecord(LocalDateTime creationDate, UUID creatorId, LocalDateTime lastModificationDate, UUID lastModifierId, UUID ownerId, String tag, UUID id, String name, String code) {
        super(Access.ACCESS);

        setCreationDate(creationDate);
        setCreatorId(creatorId);
        setLastModificationDate(lastModificationDate);
        setLastModifierId(lastModifierId);
        setOwnerId(ownerId);
        setTag(tag);
        setId(id);
        setName(name);
        setCode(code);
    }
}
