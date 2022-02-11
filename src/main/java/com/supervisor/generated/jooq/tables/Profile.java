/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables;


import com.supervisor.generated.jooq.Keys;
import com.supervisor.generated.jooq.Public;
import com.supervisor.generated.jooq.tables.records.ProfileRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row10;
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
public class Profile extends TableImpl<ProfileRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.profile</code>
     */
    public static final Profile PROFILE = new Profile();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProfileRecord> getRecordType() {
        return ProfileRecord.class;
    }

    /**
     * The column <code>public.profile.creation_date</code>.
     */
    public final TableField<ProfileRecord, LocalDateTime> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.profile.creator_id</code>.
     */
    public final TableField<ProfileRecord, UUID> CREATOR_ID = createField(DSL.name("creator_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.profile.last_modification_date</code>.
     */
    public final TableField<ProfileRecord, LocalDateTime> LAST_MODIFICATION_DATE = createField(DSL.name("last_modification_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.profile.last_modifier_id</code>.
     */
    public final TableField<ProfileRecord, UUID> LAST_MODIFIER_ID = createField(DSL.name("last_modifier_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.profile.owner_id</code>.
     */
    public final TableField<ProfileRecord, UUID> OWNER_ID = createField(DSL.name("owner_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.profile.tag</code>.
     */
    public final TableField<ProfileRecord, String> TAG = createField(DSL.name("tag"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.profile.id</code>.
     */
    public final TableField<ProfileRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.profile.name</code>.
     */
    public final TableField<ProfileRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.profile.code</code>.
     */
    public final TableField<ProfileRecord, String> CODE = createField(DSL.name("code"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.profile.parent_id</code>.
     */
    public final TableField<ProfileRecord, UUID> PARENT_ID = createField(DSL.name("parent_id"), SQLDataType.UUID, this, "");

    private Profile(Name alias, Table<ProfileRecord> aliased) {
        this(alias, aliased, null);
    }

    private Profile(Name alias, Table<ProfileRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.profile</code> table reference
     */
    public Profile(String alias) {
        this(DSL.name(alias), PROFILE);
    }

    /**
     * Create an aliased <code>public.profile</code> table reference
     */
    public Profile(Name alias) {
        this(alias, PROFILE);
    }

    /**
     * Create a <code>public.profile</code> table reference
     */
    public Profile() {
        this(DSL.name("profile"), null);
    }

    public <O extends Record> Profile(Table<O> child, ForeignKey<O, ProfileRecord> key) {
        super(child, key, PROFILE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<ProfileRecord> getPrimaryKey() {
        return Keys.PROFILE_PKEY;
    }

    @Override
    public List<ForeignKey<ProfileRecord, ?>> getReferences() {
        return Arrays.asList(Keys.PROFILE__PROFILE_PARENT_ID_FKEY);
    }

    private transient Profile _profile;

    /**
     * Get the implicit join path to the <code>public.profile</code> table.
     */
    public Profile profile() {
        if (_profile == null)
            _profile = new Profile(this, Keys.PROFILE__PROFILE_PARENT_ID_FKEY);

        return _profile;
    }

    @Override
    public Profile as(String alias) {
        return new Profile(DSL.name(alias), this);
    }

    @Override
    public Profile as(Name alias) {
        return new Profile(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Profile rename(String name) {
        return new Profile(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Profile rename(Name name) {
        return new Profile(name, null);
    }

    // -------------------------------------------------------------------------
    // Row10 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row10<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String, UUID> fieldsRow() {
        return (Row10) super.fieldsRow();
    }
}
