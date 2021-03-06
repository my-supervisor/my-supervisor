/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables;


import com.supervisor.generated.jooq.Keys;
import com.supervisor.generated.jooq.Public;
import com.supervisor.generated.jooq.tables.records.PurchaseOrderRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row8;
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
public class PurchaseOrder extends TableImpl<PurchaseOrderRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.purchase_order</code>
     */
    public static final PurchaseOrder PURCHASE_ORDER = new PurchaseOrder();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PurchaseOrderRecord> getRecordType() {
        return PurchaseOrderRecord.class;
    }

    /**
     * The column <code>public.purchase_order.creation_date</code>.
     */
    public final TableField<PurchaseOrderRecord, LocalDateTime> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.purchase_order.creator_id</code>.
     */
    public final TableField<PurchaseOrderRecord, UUID> CREATOR_ID = createField(DSL.name("creator_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.purchase_order.last_modification_date</code>.
     */
    public final TableField<PurchaseOrderRecord, LocalDateTime> LAST_MODIFICATION_DATE = createField(DSL.name("last_modification_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.purchase_order.last_modifier_id</code>.
     */
    public final TableField<PurchaseOrderRecord, UUID> LAST_MODIFIER_ID = createField(DSL.name("last_modifier_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.purchase_order.owner_id</code>.
     */
    public final TableField<PurchaseOrderRecord, UUID> OWNER_ID = createField(DSL.name("owner_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.purchase_order.tag</code>.
     */
    public final TableField<PurchaseOrderRecord, String> TAG = createField(DSL.name("tag"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.purchase_order.id</code>.
     */
    public final TableField<PurchaseOrderRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.purchase_order.state</code>.
     */
    public final TableField<PurchaseOrderRecord, String> STATE = createField(DSL.name("state"), SQLDataType.VARCHAR.nullable(false), this, "");

    private PurchaseOrder(Name alias, Table<PurchaseOrderRecord> aliased) {
        this(alias, aliased, null);
    }

    private PurchaseOrder(Name alias, Table<PurchaseOrderRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.purchase_order</code> table reference
     */
    public PurchaseOrder(String alias) {
        this(DSL.name(alias), PURCHASE_ORDER);
    }

    /**
     * Create an aliased <code>public.purchase_order</code> table reference
     */
    public PurchaseOrder(Name alias) {
        this(alias, PURCHASE_ORDER);
    }

    /**
     * Create a <code>public.purchase_order</code> table reference
     */
    public PurchaseOrder() {
        this(DSL.name("purchase_order"), null);
    }

    public <O extends Record> PurchaseOrder(Table<O> child, ForeignKey<O, PurchaseOrderRecord> key) {
        super(child, key, PURCHASE_ORDER);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<PurchaseOrderRecord> getPrimaryKey() {
        return Keys.PURCHASE_ORDER_PKEY;
    }

    @Override
    public List<ForeignKey<PurchaseOrderRecord, ?>> getReferences() {
        return Arrays.asList(Keys.PURCHASE_ORDER__PURCHASE_ORDER_ID_FKEY);
    }

    private transient Order _order;

    /**
     * Get the implicit join path to the <code>public.order</code> table.
     */
    public Order order() {
        if (_order == null)
            _order = new Order(this, Keys.PURCHASE_ORDER__PURCHASE_ORDER_ID_FKEY);

        return _order;
    }

    @Override
    public PurchaseOrder as(String alias) {
        return new PurchaseOrder(DSL.name(alias), this);
    }

    @Override
    public PurchaseOrder as(Name alias) {
        return new PurchaseOrder(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public PurchaseOrder rename(String name) {
        return new PurchaseOrder(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public PurchaseOrder rename(Name name) {
        return new PurchaseOrder(name, null);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String> fieldsRow() {
        return (Row8) super.fieldsRow();
    }
}
