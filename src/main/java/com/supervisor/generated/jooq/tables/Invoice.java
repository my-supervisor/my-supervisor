/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables;


import com.supervisor.generated.jooq.Keys;
import com.supervisor.generated.jooq.Public;
import com.supervisor.generated.jooq.tables.records.InvoiceRecord;

import java.time.LocalDate;
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
public class Invoice extends TableImpl<InvoiceRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.invoice</code>
     */
    public static final Invoice INVOICE = new Invoice();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<InvoiceRecord> getRecordType() {
        return InvoiceRecord.class;
    }

    /**
     * The column <code>public.invoice.creation_date</code>.
     */
    public final TableField<InvoiceRecord, LocalDateTime> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.invoice.creator_id</code>.
     */
    public final TableField<InvoiceRecord, UUID> CREATOR_ID = createField(DSL.name("creator_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.invoice.last_modification_date</code>.
     */
    public final TableField<InvoiceRecord, LocalDateTime> LAST_MODIFICATION_DATE = createField(DSL.name("last_modification_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.invoice.last_modifier_id</code>.
     */
    public final TableField<InvoiceRecord, UUID> LAST_MODIFIER_ID = createField(DSL.name("last_modifier_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.invoice.owner_id</code>.
     */
    public final TableField<InvoiceRecord, UUID> OWNER_ID = createField(DSL.name("owner_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.invoice.tag</code>.
     */
    public final TableField<InvoiceRecord, String> TAG = createField(DSL.name("tag"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.invoice.id</code>.
     */
    public final TableField<InvoiceRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.invoice.due_date</code>.
     */
    public final TableField<InvoiceRecord, LocalDate> DUE_DATE = createField(DSL.name("due_date"), SQLDataType.LOCALDATE.nullable(false), this, "");

    /**
     * The column <code>public.invoice.purchase_order_id</code>.
     */
    public final TableField<InvoiceRecord, UUID> PURCHASE_ORDER_ID = createField(DSL.name("purchase_order_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.invoice.state</code>.
     */
    public final TableField<InvoiceRecord, String> STATE = createField(DSL.name("state"), SQLDataType.VARCHAR.nullable(false), this, "");

    private Invoice(Name alias, Table<InvoiceRecord> aliased) {
        this(alias, aliased, null);
    }

    private Invoice(Name alias, Table<InvoiceRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.invoice</code> table reference
     */
    public Invoice(String alias) {
        this(DSL.name(alias), INVOICE);
    }

    /**
     * Create an aliased <code>public.invoice</code> table reference
     */
    public Invoice(Name alias) {
        this(alias, INVOICE);
    }

    /**
     * Create a <code>public.invoice</code> table reference
     */
    public Invoice() {
        this(DSL.name("invoice"), null);
    }

    public <O extends Record> Invoice(Table<O> child, ForeignKey<O, InvoiceRecord> key) {
        super(child, key, INVOICE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<InvoiceRecord> getPrimaryKey() {
        return Keys.INVOICE_PKEY;
    }

    @Override
    public List<ForeignKey<InvoiceRecord, ?>> getReferences() {
        return Arrays.asList(Keys.INVOICE__INVOICE_ID_FKEY, Keys.INVOICE__INVOICE_PURCHASE_ORDER_ID_FKEY);
    }

    private transient Order _order;
    private transient PurchaseOrder _purchaseOrder;

    /**
     * Get the implicit join path to the <code>public.order</code> table.
     */
    public Order order() {
        if (_order == null)
            _order = new Order(this, Keys.INVOICE__INVOICE_ID_FKEY);

        return _order;
    }

    /**
     * Get the implicit join path to the <code>public.purchase_order</code>
     * table.
     */
    public PurchaseOrder purchaseOrder() {
        if (_purchaseOrder == null)
            _purchaseOrder = new PurchaseOrder(this, Keys.INVOICE__INVOICE_PURCHASE_ORDER_ID_FKEY);

        return _purchaseOrder;
    }

    @Override
    public Invoice as(String alias) {
        return new Invoice(DSL.name(alias), this);
    }

    @Override
    public Invoice as(Name alias) {
        return new Invoice(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Invoice rename(String name) {
        return new Invoice(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Invoice rename(Name name) {
        return new Invoice(name, null);
    }

    // -------------------------------------------------------------------------
    // Row10 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row10<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, LocalDate, UUID, String> fieldsRow() {
        return (Row10) super.fieldsRow();
    }
}
