/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables.records;


import com.supervisor.generated.jooq.tables.Language;

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
public class LanguageRecord extends UpdatableRecordImpl<LanguageRecord> implements Record11<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String, String, Boolean> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.language.creation_date</code>.
     */
    public void setCreationDate(LocalDateTime value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.language.creation_date</code>.
     */
    public LocalDateTime getCreationDate() {
        return (LocalDateTime) get(0);
    }

    /**
     * Setter for <code>public.language.creator_id</code>.
     */
    public void setCreatorId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.language.creator_id</code>.
     */
    public UUID getCreatorId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>public.language.last_modification_date</code>.
     */
    public void setLastModificationDate(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.language.last_modification_date</code>.
     */
    public LocalDateTime getLastModificationDate() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>public.language.last_modifier_id</code>.
     */
    public void setLastModifierId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.language.last_modifier_id</code>.
     */
    public UUID getLastModifierId() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>public.language.owner_id</code>.
     */
    public void setOwnerId(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.language.owner_id</code>.
     */
    public UUID getOwnerId() {
        return (UUID) get(4);
    }

    /**
     * Setter for <code>public.language.tag</code>.
     */
    public void setTag(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.language.tag</code>.
     */
    public String getTag() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.language.id</code>.
     */
    public void setId(UUID value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.language.id</code>.
     */
    public UUID getId() {
        return (UUID) get(6);
    }

    /**
     * Setter for <code>public.language.iso_code</code>.
     */
    public void setIsoCode(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.language.iso_code</code>.
     */
    public String getIsoCode() {
        return (String) get(7);
    }

    /**
     * Setter for <code>public.language.code</code>.
     */
    public void setCode(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.language.code</code>.
     */
    public String getCode() {
        return (String) get(8);
    }

    /**
     * Setter for <code>public.language.name</code>.
     */
    public void setName(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.language.name</code>.
     */
    public String getName() {
        return (String) get(9);
    }

    /**
     * Setter for <code>public.language.enabled</code>.
     */
    public void setEnabled(Boolean value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.language.enabled</code>.
     */
    public Boolean getEnabled() {
        return (Boolean) get(10);
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
    public Row11<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String, String, Boolean> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    @Override
    public Row11<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String, String, Boolean> valuesRow() {
        return (Row11) super.valuesRow();
    }

    @Override
    public Field<LocalDateTime> field1() {
        return Language.LANGUAGE.CREATION_DATE;
    }

    @Override
    public Field<UUID> field2() {
        return Language.LANGUAGE.CREATOR_ID;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return Language.LANGUAGE.LAST_MODIFICATION_DATE;
    }

    @Override
    public Field<UUID> field4() {
        return Language.LANGUAGE.LAST_MODIFIER_ID;
    }

    @Override
    public Field<UUID> field5() {
        return Language.LANGUAGE.OWNER_ID;
    }

    @Override
    public Field<String> field6() {
        return Language.LANGUAGE.TAG;
    }

    @Override
    public Field<UUID> field7() {
        return Language.LANGUAGE.ID;
    }

    @Override
    public Field<String> field8() {
        return Language.LANGUAGE.ISO_CODE;
    }

    @Override
    public Field<String> field9() {
        return Language.LANGUAGE.CODE;
    }

    @Override
    public Field<String> field10() {
        return Language.LANGUAGE.NAME;
    }

    @Override
    public Field<Boolean> field11() {
        return Language.LANGUAGE.ENABLED;
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
        return getIsoCode();
    }

    @Override
    public String component9() {
        return getCode();
    }

    @Override
    public String component10() {
        return getName();
    }

    @Override
    public Boolean component11() {
        return getEnabled();
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
        return getIsoCode();
    }

    @Override
    public String value9() {
        return getCode();
    }

    @Override
    public String value10() {
        return getName();
    }

    @Override
    public Boolean value11() {
        return getEnabled();
    }

    @Override
    public LanguageRecord value1(LocalDateTime value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public LanguageRecord value2(UUID value) {
        setCreatorId(value);
        return this;
    }

    @Override
    public LanguageRecord value3(LocalDateTime value) {
        setLastModificationDate(value);
        return this;
    }

    @Override
    public LanguageRecord value4(UUID value) {
        setLastModifierId(value);
        return this;
    }

    @Override
    public LanguageRecord value5(UUID value) {
        setOwnerId(value);
        return this;
    }

    @Override
    public LanguageRecord value6(String value) {
        setTag(value);
        return this;
    }

    @Override
    public LanguageRecord value7(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public LanguageRecord value8(String value) {
        setIsoCode(value);
        return this;
    }

    @Override
    public LanguageRecord value9(String value) {
        setCode(value);
        return this;
    }

    @Override
    public LanguageRecord value10(String value) {
        setName(value);
        return this;
    }

    @Override
    public LanguageRecord value11(Boolean value) {
        setEnabled(value);
        return this;
    }

    @Override
    public LanguageRecord values(LocalDateTime value1, UUID value2, LocalDateTime value3, UUID value4, UUID value5, String value6, UUID value7, String value8, String value9, String value10, Boolean value11) {
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
     * Create a detached LanguageRecord
     */
    public LanguageRecord() {
        super(Language.LANGUAGE);
    }

    /**
     * Create a detached, initialised LanguageRecord
     */
    public LanguageRecord(LocalDateTime creationDate, UUID creatorId, LocalDateTime lastModificationDate, UUID lastModifierId, UUID ownerId, String tag, UUID id, String isoCode, String code, String name, Boolean enabled) {
        super(Language.LANGUAGE);

        setCreationDate(creationDate);
        setCreatorId(creatorId);
        setLastModificationDate(lastModificationDate);
        setLastModifierId(lastModifierId);
        setOwnerId(ownerId);
        setTag(tag);
        setId(id);
        setIsoCode(isoCode);
        setCode(code);
        setName(name);
        setEnabled(enabled);
    }
}
