/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables;


import com.supervisor.generated.jooq.Keys;
import com.supervisor.generated.jooq.Public;
import com.supervisor.generated.jooq.tables.records.ActivityTemplateLikeRecord;

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
public class ActivityTemplateLike extends TableImpl<ActivityTemplateLikeRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.activity_template_like</code>
     */
    public static final ActivityTemplateLike ACTIVITY_TEMPLATE_LIKE = new ActivityTemplateLike();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ActivityTemplateLikeRecord> getRecordType() {
        return ActivityTemplateLikeRecord.class;
    }

    /**
     * The column <code>public.activity_template_like.creation_date</code>.
     */
    public final TableField<ActivityTemplateLikeRecord, LocalDateTime> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.activity_template_like.creator_id</code>.
     */
    public final TableField<ActivityTemplateLikeRecord, UUID> CREATOR_ID = createField(DSL.name("creator_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column
     * <code>public.activity_template_like.last_modification_date</code>.
     */
    public final TableField<ActivityTemplateLikeRecord, LocalDateTime> LAST_MODIFICATION_DATE = createField(DSL.name("last_modification_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.activity_template_like.last_modifier_id</code>.
     */
    public final TableField<ActivityTemplateLikeRecord, UUID> LAST_MODIFIER_ID = createField(DSL.name("last_modifier_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.activity_template_like.owner_id</code>.
     */
    public final TableField<ActivityTemplateLikeRecord, UUID> OWNER_ID = createField(DSL.name("owner_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.activity_template_like.tag</code>.
     */
    public final TableField<ActivityTemplateLikeRecord, String> TAG = createField(DSL.name("tag"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.activity_template_like.id</code>.
     */
    public final TableField<ActivityTemplateLikeRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.activity_template_like.template_id</code>.
     */
    public final TableField<ActivityTemplateLikeRecord, UUID> TEMPLATE_ID = createField(DSL.name("template_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.activity_template_like.user_id</code>.
     */
    public final TableField<ActivityTemplateLikeRecord, UUID> USER_ID = createField(DSL.name("user_id"), SQLDataType.UUID.nullable(false), this, "");

    private ActivityTemplateLike(Name alias, Table<ActivityTemplateLikeRecord> aliased) {
        this(alias, aliased, null);
    }

    private ActivityTemplateLike(Name alias, Table<ActivityTemplateLikeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.activity_template_like</code> table
     * reference
     */
    public ActivityTemplateLike(String alias) {
        this(DSL.name(alias), ACTIVITY_TEMPLATE_LIKE);
    }

    /**
     * Create an aliased <code>public.activity_template_like</code> table
     * reference
     */
    public ActivityTemplateLike(Name alias) {
        this(alias, ACTIVITY_TEMPLATE_LIKE);
    }

    /**
     * Create a <code>public.activity_template_like</code> table reference
     */
    public ActivityTemplateLike() {
        this(DSL.name("activity_template_like"), null);
    }

    public <O extends Record> ActivityTemplateLike(Table<O> child, ForeignKey<O, ActivityTemplateLikeRecord> key) {
        super(child, key, ACTIVITY_TEMPLATE_LIKE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<ActivityTemplateLikeRecord> getPrimaryKey() {
        return Keys.ACTIVITY_TEMPLATE_LIKE_PKEY;
    }

    @Override
    public List<ForeignKey<ActivityTemplateLikeRecord, ?>> getReferences() {
        return Arrays.asList(Keys.ACTIVITY_TEMPLATE_LIKE__ACTIVITY_TEMPLATE_LIKE_TEMPLATE_ID_FKEY, Keys.ACTIVITY_TEMPLATE_LIKE__ACTIVITY_TEMPLATE_LIKE_USER_ID_FKEY);
    }

    private transient ActivityTemplatePublished _activityTemplatePublished;
    private transient User _user;

    /**
     * Get the implicit join path to the
     * <code>public.activity_template_published</code> table.
     */
    public ActivityTemplatePublished activityTemplatePublished() {
        if (_activityTemplatePublished == null)
            _activityTemplatePublished = new ActivityTemplatePublished(this, Keys.ACTIVITY_TEMPLATE_LIKE__ACTIVITY_TEMPLATE_LIKE_TEMPLATE_ID_FKEY);

        return _activityTemplatePublished;
    }

    /**
     * Get the implicit join path to the <code>public.user</code> table.
     */
    public User user() {
        if (_user == null)
            _user = new User(this, Keys.ACTIVITY_TEMPLATE_LIKE__ACTIVITY_TEMPLATE_LIKE_USER_ID_FKEY);

        return _user;
    }

    @Override
    public ActivityTemplateLike as(String alias) {
        return new ActivityTemplateLike(DSL.name(alias), this);
    }

    @Override
    public ActivityTemplateLike as(Name alias) {
        return new ActivityTemplateLike(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ActivityTemplateLike rename(String name) {
        return new ActivityTemplateLike(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ActivityTemplateLike rename(Name name) {
        return new ActivityTemplateLike(name, null);
    }

    // -------------------------------------------------------------------------
    // Row9 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row9<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, UUID, UUID> fieldsRow() {
        return (Row9) super.fieldsRow();
    }
}
