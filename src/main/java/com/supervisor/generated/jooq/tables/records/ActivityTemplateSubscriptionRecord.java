/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables.records;


import com.supervisor.generated.jooq.tables.ActivityTemplateSubscription;

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
public class ActivityTemplateSubscriptionRecord extends UpdatableRecordImpl<ActivityTemplateSubscriptionRecord> implements Record9<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, UUID, UUID> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for
     * <code>public.activity_template_subscription.creation_date</code>.
     */
    public void setCreationDate(LocalDateTime value) {
        set(0, value);
    }

    /**
     * Getter for
     * <code>public.activity_template_subscription.creation_date</code>.
     */
    public LocalDateTime getCreationDate() {
        return (LocalDateTime) get(0);
    }

    /**
     * Setter for <code>public.activity_template_subscription.creator_id</code>.
     */
    public void setCreatorId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.activity_template_subscription.creator_id</code>.
     */
    public UUID getCreatorId() {
        return (UUID) get(1);
    }

    /**
     * Setter for
     * <code>public.activity_template_subscription.last_modification_date</code>.
     */
    public void setLastModificationDate(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for
     * <code>public.activity_template_subscription.last_modification_date</code>.
     */
    public LocalDateTime getLastModificationDate() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for
     * <code>public.activity_template_subscription.last_modifier_id</code>.
     */
    public void setLastModifierId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for
     * <code>public.activity_template_subscription.last_modifier_id</code>.
     */
    public UUID getLastModifierId() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>public.activity_template_subscription.owner_id</code>.
     */
    public void setOwnerId(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.activity_template_subscription.owner_id</code>.
     */
    public UUID getOwnerId() {
        return (UUID) get(4);
    }

    /**
     * Setter for <code>public.activity_template_subscription.tag</code>.
     */
    public void setTag(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.activity_template_subscription.tag</code>.
     */
    public String getTag() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.activity_template_subscription.id</code>.
     */
    public void setId(UUID value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.activity_template_subscription.id</code>.
     */
    public UUID getId() {
        return (UUID) get(6);
    }

    /**
     * Setter for
     * <code>public.activity_template_subscription.template_id</code>.
     */
    public void setTemplateId(UUID value) {
        set(7, value);
    }

    /**
     * Getter for
     * <code>public.activity_template_subscription.template_id</code>.
     */
    public UUID getTemplateId() {
        return (UUID) get(7);
    }

    /**
     * Setter for <code>public.activity_template_subscription.user_id</code>.
     */
    public void setUserId(UUID value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.activity_template_subscription.user_id</code>.
     */
    public UUID getUserId() {
        return (UUID) get(8);
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
    public Row9<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, UUID, UUID> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    @Override
    public Row9<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, UUID, UUID> valuesRow() {
        return (Row9) super.valuesRow();
    }

    @Override
    public Field<LocalDateTime> field1() {
        return ActivityTemplateSubscription.ACTIVITY_TEMPLATE_SUBSCRIPTION.CREATION_DATE;
    }

    @Override
    public Field<UUID> field2() {
        return ActivityTemplateSubscription.ACTIVITY_TEMPLATE_SUBSCRIPTION.CREATOR_ID;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return ActivityTemplateSubscription.ACTIVITY_TEMPLATE_SUBSCRIPTION.LAST_MODIFICATION_DATE;
    }

    @Override
    public Field<UUID> field4() {
        return ActivityTemplateSubscription.ACTIVITY_TEMPLATE_SUBSCRIPTION.LAST_MODIFIER_ID;
    }

    @Override
    public Field<UUID> field5() {
        return ActivityTemplateSubscription.ACTIVITY_TEMPLATE_SUBSCRIPTION.OWNER_ID;
    }

    @Override
    public Field<String> field6() {
        return ActivityTemplateSubscription.ACTIVITY_TEMPLATE_SUBSCRIPTION.TAG;
    }

    @Override
    public Field<UUID> field7() {
        return ActivityTemplateSubscription.ACTIVITY_TEMPLATE_SUBSCRIPTION.ID;
    }

    @Override
    public Field<UUID> field8() {
        return ActivityTemplateSubscription.ACTIVITY_TEMPLATE_SUBSCRIPTION.TEMPLATE_ID;
    }

    @Override
    public Field<UUID> field9() {
        return ActivityTemplateSubscription.ACTIVITY_TEMPLATE_SUBSCRIPTION.USER_ID;
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
    public UUID component8() {
        return getTemplateId();
    }

    @Override
    public UUID component9() {
        return getUserId();
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
    public UUID value8() {
        return getTemplateId();
    }

    @Override
    public UUID value9() {
        return getUserId();
    }

    @Override
    public ActivityTemplateSubscriptionRecord value1(LocalDateTime value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public ActivityTemplateSubscriptionRecord value2(UUID value) {
        setCreatorId(value);
        return this;
    }

    @Override
    public ActivityTemplateSubscriptionRecord value3(LocalDateTime value) {
        setLastModificationDate(value);
        return this;
    }

    @Override
    public ActivityTemplateSubscriptionRecord value4(UUID value) {
        setLastModifierId(value);
        return this;
    }

    @Override
    public ActivityTemplateSubscriptionRecord value5(UUID value) {
        setOwnerId(value);
        return this;
    }

    @Override
    public ActivityTemplateSubscriptionRecord value6(String value) {
        setTag(value);
        return this;
    }

    @Override
    public ActivityTemplateSubscriptionRecord value7(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public ActivityTemplateSubscriptionRecord value8(UUID value) {
        setTemplateId(value);
        return this;
    }

    @Override
    public ActivityTemplateSubscriptionRecord value9(UUID value) {
        setUserId(value);
        return this;
    }

    @Override
    public ActivityTemplateSubscriptionRecord values(LocalDateTime value1, UUID value2, LocalDateTime value3, UUID value4, UUID value5, String value6, UUID value7, UUID value8, UUID value9) {
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
     * Create a detached ActivityTemplateSubscriptionRecord
     */
    public ActivityTemplateSubscriptionRecord() {
        super(ActivityTemplateSubscription.ACTIVITY_TEMPLATE_SUBSCRIPTION);
    }

    /**
     * Create a detached, initialised ActivityTemplateSubscriptionRecord
     */
    public ActivityTemplateSubscriptionRecord(LocalDateTime creationDate, UUID creatorId, LocalDateTime lastModificationDate, UUID lastModifierId, UUID ownerId, String tag, UUID id, UUID templateId, UUID userId) {
        super(ActivityTemplateSubscription.ACTIVITY_TEMPLATE_SUBSCRIPTION);

        setCreationDate(creationDate);
        setCreatorId(creatorId);
        setLastModificationDate(lastModificationDate);
        setLastModifierId(lastModifierId);
        setOwnerId(ownerId);
        setTag(tag);
        setId(id);
        setTemplateId(templateId);
        setUserId(userId);
    }
}
