/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables;


import com.supervisor.generated.jooq.Keys;
import com.supervisor.generated.jooq.Public;
import com.supervisor.generated.jooq.tables.records.ProductCatalogRecord;

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
public class ProductCatalog extends TableImpl<ProductCatalogRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.product_catalog</code>
     */
    public static final ProductCatalog PRODUCT_CATALOG = new ProductCatalog();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProductCatalogRecord> getRecordType() {
        return ProductCatalogRecord.class;
    }

    /**
     * The column <code>public.product_catalog.creation_date</code>.
     */
    public final TableField<ProductCatalogRecord, LocalDateTime> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.product_catalog.creator_id</code>.
     */
    public final TableField<ProductCatalogRecord, UUID> CREATOR_ID = createField(DSL.name("creator_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.product_catalog.last_modification_date</code>.
     */
    public final TableField<ProductCatalogRecord, LocalDateTime> LAST_MODIFICATION_DATE = createField(DSL.name("last_modification_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.product_catalog.last_modifier_id</code>.
     */
    public final TableField<ProductCatalogRecord, UUID> LAST_MODIFIER_ID = createField(DSL.name("last_modifier_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.product_catalog.owner_id</code>.
     */
    public final TableField<ProductCatalogRecord, UUID> OWNER_ID = createField(DSL.name("owner_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.product_catalog.tag</code>.
     */
    public final TableField<ProductCatalogRecord, String> TAG = createField(DSL.name("tag"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.product_catalog.id</code>.
     */
    public final TableField<ProductCatalogRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.product_catalog.name</code>.
     */
    public final TableField<ProductCatalogRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.product_catalog.currency_id</code>.
     */
    public final TableField<ProductCatalogRecord, UUID> CURRENCY_ID = createField(DSL.name("currency_id"), SQLDataType.UUID.nullable(false), this, "");

    private ProductCatalog(Name alias, Table<ProductCatalogRecord> aliased) {
        this(alias, aliased, null);
    }

    private ProductCatalog(Name alias, Table<ProductCatalogRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.product_catalog</code> table reference
     */
    public ProductCatalog(String alias) {
        this(DSL.name(alias), PRODUCT_CATALOG);
    }

    /**
     * Create an aliased <code>public.product_catalog</code> table reference
     */
    public ProductCatalog(Name alias) {
        this(alias, PRODUCT_CATALOG);
    }

    /**
     * Create a <code>public.product_catalog</code> table reference
     */
    public ProductCatalog() {
        this(DSL.name("product_catalog"), null);
    }

    public <O extends Record> ProductCatalog(Table<O> child, ForeignKey<O, ProductCatalogRecord> key) {
        super(child, key, PRODUCT_CATALOG);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<ProductCatalogRecord> getPrimaryKey() {
        return Keys.PRODUCT_CATALOG_PKEY;
    }

    @Override
    public List<ForeignKey<ProductCatalogRecord, ?>> getReferences() {
        return Arrays.asList(Keys.PRODUCT_CATALOG__PRODUCT_CATALOG_CURRENCY_ID_FKEY);
    }

    private transient Currency _currency;

    /**
     * Get the implicit join path to the <code>public.currency</code> table.
     */
    public Currency currency() {
        if (_currency == null)
            _currency = new Currency(this, Keys.PRODUCT_CATALOG__PRODUCT_CATALOG_CURRENCY_ID_FKEY);

        return _currency;
    }

    @Override
    public ProductCatalog as(String alias) {
        return new ProductCatalog(DSL.name(alias), this);
    }

    @Override
    public ProductCatalog as(Name alias) {
        return new ProductCatalog(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ProductCatalog rename(String name) {
        return new ProductCatalog(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ProductCatalog rename(Name name) {
        return new ProductCatalog(name, null);
    }

    // -------------------------------------------------------------------------
    // Row9 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row9<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, UUID> fieldsRow() {
        return (Row9) super.fieldsRow();
    }
}