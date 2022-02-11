/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables.records;


import com.supervisor.generated.jooq.tables.Event;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class EventRecord extends UpdatableRecordImpl<EventRecord> implements Record11<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.event.creation_date</code>.
     */
    public void setCreationDate(LocalDateTime value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.event.creation_date</code>.
     */
    public LocalDateTime getCreationDate() {
        return (LocalDateTime) get(0);
    }

    /**
     * Setter for <code>public.event.creator_id</code>.
     */
    public void setCreatorId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.event.creator_id</code>.
     */
    public UUID getCreatorId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>public.event.last_modification_date</code>.
     */
    public void setLastModificationDate(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.event.last_modification_date</code>.
     */
    public LocalDateTime getLastModificationDate() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>public.event.last_modifier_id</code>.
     */
    public void setLastModifierId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.event.last_modifier_id</code>.
     */
    public UUID getLastModifierId() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>public.event.owner_id</code>.
     */
    public void setOwnerId(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.event.owner_id</code>.
     */
    public UUID getOwnerId() {
        return (UUID) get(4);
    }

    /**
     * Setter for <code>public.event.tag</code>.
     */
    public void setTag(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.event.tag</code>.
     */
    public String getTag() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.event.id</code>.
     */
    public void setId(UUID value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.event.id</code>.
     */
    public UUID getId() {
        return (UUID) get(6);
    }

    /**
     * Setter for <code>public.event.information</code>.
     */
    public void setInformation(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.event.information</code>.
     */
    public String getInformation() {
        return (String) get(7);
    }

    /**
     * Setter for <code>public.event.details</code>.
     */
    public void setDetails(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.event.details</code>.
     */
    public String getDetails() {
        return (String) get(8);
    }

    /**
     * Setter for <code>public.event.identifier</code>.
     */
    public void setIdentifier(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.event.identifier</code>.
     */
    public String getIdentifier() {
        return (String) get(9);
    }

    /**
     * Setter for <code>public.event.level</code>.
     */
    public void setLevel(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.event.level</code>.
     */
    public String getLevel() {
        return (String) get(10);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record11 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row11<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String, String, String> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    @Override
    public Row11<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String, String, String> valuesRow() {
        return (Row11) super.valuesRow();
    }

    @Override
    public Field<LocalDateTime> field1() {
        return Event.EVENT.CREATION_DATE;
    }

    @Override
    public Field<UUID> field2() {
        return Event.EVENT.CREATOR_ID;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return Event.EVENT.LAST_MODIFICATION_DATE;
    }

    @Override
    public Field<UUID> field4() {
        return Event.EVENT.LAST_MODIFIER_ID;
    }

    @Override
    public Field<UUID> field5() {
        return Event.EVENT.OWNER_ID;
    }

    @Override
    public Field<String> field6() {
        return Event.EVENT.TAG;
    }

    @Override
    public Field<UUID> field7() {
        return Event.EVENT.ID;
    }

    @Override
    public Field<String> field8() {
        return Event.EVENT.INFORMATION;
    }

    @Override
    public Field<String> field9() {
        return Event.EVENT.DETAILS;
    }

    @Override
    public Field<String> field10() {
        return Event.EVENT.IDENTIFIER;
    }

    @Override
    public Field<String> field11() {
        return Event.EVENT.LEVEL;
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
        return getInformation();
    }

    @Override
    public String component9() {
        return getDetails();
    }

    @Override
    public String component10() {
        return getIdentifier();
    }

    @Override
    public String component11() {
        return getLevel();
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
        return getInformation();
    }

    @Override
    public String value9() {
        return getDetails();
    }

    @Override
    public String value10() {
        return getIdentifier();
    }

    @Override
    public String value11() {
        return getLevel();
    }

    @Override
    public EventRecord value1(LocalDateTime value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public EventRecord value2(UUID value) {
        setCreatorId(value);
        return this;
    }

    @Override
    public EventRecord value3(LocalDateTime value) {
        setLastModificationDate(value);
        return this;
    }

    @Override
    public EventRecord value4(UUID value) {
        setLastModifierId(value);
        return this;
    }

    @Override
    public EventRecord value5(UUID value) {
        setOwnerId(value);
        return this;
    }

    @Override
    public EventRecord value6(String value) {
        setTag(value);
        return this;
    }

    @Override
    public EventRecord value7(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public EventRecord value8(String value) {
        setInformation(value);
        return this;
    }

    @Override
    public EventRecord value9(String value) {
        setDetails(value);
        return this;
    }

    @Override
    public EventRecord value10(String value) {
        setIdentifier(value);
        return this;
    }

    @Override
    public EventRecord value11(String value) {
        setLevel(value);
        return this;
    }

    @Override
    public EventRecord values(LocalDateTime value1, UUID value2, LocalDateTime value3, UUID value4, UUID value5, String value6, UUID value7, String value8, String value9, String value10, String value11) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached EventRecord
     */
    public EventRecord() {
        super(Event.EVENT);
    }

    /**
     * Create a detached, initialised EventRecord
     */
    public EventRecord(LocalDateTime creationDate, UUID creatorId, LocalDateTime lastModificationDate, UUID lastModifierId, UUID ownerId, String tag, UUID id, String information, String details, String identifier, String level) {
        super(Event.EVENT);

        setCreationDate(creationDate);
        setCreatorId(creatorId);
        setLastModificationDate(lastModificationDate);
        setLastModifierId(lastModifierId);
        setOwnerId(ownerId);
        setTag(tag);
        setId(id);
        setInformation(information);
        setDetails(details);
        setIdentifier(identifier);
        setLevel(level);
    }
}
