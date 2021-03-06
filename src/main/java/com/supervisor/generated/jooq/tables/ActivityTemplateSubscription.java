/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables;


import com.supervisor.generated.jooq.Keys;
import com.supervisor.generated.jooq.Public;
import com.supervisor.generated.jooq.tables.records.ActivityTemplateSubscriptionRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row9;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ActivityTemplateSubscription extends TableImpl<ActivityTemplateSubscriptionRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of
     * <code>public.activity_template_subscription</code>
     */
    public static final ActivityTemplateSubscription ACTIVITY_TEMPLATE_SUBSCRIPTION = new ActivityTemplateSubscription();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ActivityTemplateSubscriptionRecord> getRecordType() {
        return ActivityTemplateSubscriptionRecord.class;
    }

    /**
     * The column
     * <code>public.activity_template_subscription.creation_date</code>.
     */
    public final TableField<ActivityTemplateSubscriptionRecord, LocalDateTime> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.activity_template_subscription.creator_id</code>.
     */
    public final TableField<ActivityTemplateSubscriptionRecord, UUID> CREATOR_ID = createField(DSL.name("creator_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column
     * <code>public.activity_template_subscription.last_modification_date</code>.
     */
    public final TableField<ActivityTemplateSubscriptionRecord, LocalDateTime> LAST_MODIFICATION_DATE = createField(DSL.name("last_modification_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column
     * <code>public.activity_template_subscription.last_modifier_id</code>.
     */
    public final TableField<ActivityTemplateSubscriptionRecord, UUID> LAST_MODIFIER_ID = createField(DSL.name("last_modifier_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.activity_template_subscription.owner_id</code>.
     */
    public final TableField<ActivityTemplateSubscriptionRecord, UUID> OWNER_ID = createField(DSL.name("owner_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.activity_template_subscription.tag</code>.
     */
    public final TableField<ActivityTemplateSubscriptionRecord, String> TAG = createField(DSL.name("tag"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.activity_template_subscription.id</code>.
     */
    public final TableField<ActivityTemplateSubscriptionRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column
     * <code>public.activity_template_subscription.template_id</code>.
     */
    public final TableField<ActivityTemplateSubscriptionRecord, UUID> TEMPLATE_ID = createField(DSL.name("template_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.activity_template_subscription.user_id</code>.
     */
    public final TableField<ActivityTemplateSubscriptionRecord, UUID> USER_ID = createField(DSL.name("user_id"), SQLDataType.UUID.nullable(false), this, "");

    private ActivityTemplateSubscription(Name alias, Table<ActivityTemplateSubscriptionRecord> aliased) {
        this(alias, aliased, null);
    }

    private ActivityTemplateSubscription(Name alias, Table<ActivityTemplateSubscriptionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.activity_template_subscription</code>
     * table reference
     */
    public ActivityTemplateSubscription(String alias) {
        this(DSL.name(alias), ACTIVITY_TEMPLATE_SUBSCRIPTION);
    }

    /**
     * Create an aliased <code>public.activity_template_subscription</code>
     * table reference
     */
    public ActivityTemplateSubscription(Name alias) {
        this(alias, ACTIVITY_TEMPLATE_SUBSCRIPTION);
    }

    /**
     * Create a <code>public.activity_template_subscription</code> table
     * reference
     */
    public ActivityTemplateSubscription() {
        this(DSL.name("activity_template_subscription"), null);
    }

    public <O extends Record> ActivityTemplateSubscription(Table<O> child, ForeignKey<O, ActivityTemplateSubscriptionRecord> key) {
        super(child, key, ACTIVITY_TEMPLATE_SUBSCRIPTION);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<ActivityTemplateSubscriptionRecord> getPrimaryKey() {
        return Keys.ACTIVITY_TEMPLATE_SUBSCRIPTION_PKEY;
    }

    @Override
    public List<ForeignKey<ActivityTemplateSubscriptionRecord, ?>> getReferences() {
        return Arrays.asList(Keys.ACTIVITY_TEMPLATE_SUBSCRIPTION__ACTIVITY_TEMPLATE_SUBSCRIPTION_TEMPLATE_ID_FKEY, Keys.ACTIVITY_TEMPLATE_SUBSCRIPTION__ACTIVITY_TEMPLATE_SUBSCRIPTION_USER_ID_FKEY);
    }

    private transient ActivityTemplatePublished _activityTemplatePublished;
    private transient User _user;

    /**
     * Get the implicit join path to the
     * <code>public.activity_template_published</code> table.
     */
    public ActivityTemplatePublished activityTemplatePublished() {
        if (_activityTemplatePublished == null)
            _activityTemplatePublished = new ActivityTemplatePublished(this, Keys.ACTIVITY_TEMPLATE_SUBSCRIPTION__ACTIVITY_TEMPLATE_SUBSCRIPTION_TEMPLATE_ID_FKEY);

        return _activityTemplatePublished;
    }

    /**
     * Get the implicit join path to the <code>public.user</code> table.
     */
    public User user() {
        if (_user == null)
            _user = new User(this, Keys.ACTIVITY_TEMPLATE_SUBSCRIPTION__ACTIVITY_TEMPLATE_SUBSCRIPTION_USER_ID_FKEY);

        return _user;
    }

    @Override
    public ActivityTemplateSubscription as(String alias) {
        return new ActivityTemplateSubscription(DSL.name(alias), this);
    }

    @Override
    public ActivityTemplateSubscription as(Name alias) {
        return new ActivityTemplateSubscription(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ActivityTemplateSubscription rename(String name) {
        return new ActivityTemplateSubscription(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ActivityTemplateSubscription rename(Name name) {
        return new ActivityTemplateSubscription(name, null);
    }

    // -------------------------------------------------------------------------
    // Row9 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row9<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, UUID, UUID> fieldsRow() {
        return (Row9) super.fieldsRow();
    }
}
