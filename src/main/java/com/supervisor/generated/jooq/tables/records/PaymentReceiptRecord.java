/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq.tables.records;


import com.supervisor.generated.jooq.tables.PaymentReceipt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record19;
import org.jooq.Row19;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PaymentReceiptRecord extends UpdatableRecordImpl<PaymentReceiptRecord> implements Record19<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String, String, UUID, LocalDate, String, UUID, String, Double, UUID, UUID, UUID> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.payment_receipt.creation_date</code>.
     */
    public void setCreationDate(LocalDateTime value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.payment_receipt.creation_date</code>.
     */
    public LocalDateTime getCreationDate() {
        return (LocalDateTime) get(0);
    }

    /**
     * Setter for <code>public.payment_receipt.creator_id</code>.
     */
    public void setCreatorId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.payment_receipt.creator_id</code>.
     */
    public UUID getCreatorId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>public.payment_receipt.last_modification_date</code>.
     */
    public void setLastModificationDate(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.payment_receipt.last_modification_date</code>.
     */
    public LocalDateTime getLastModificationDate() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>public.payment_receipt.last_modifier_id</code>.
     */
    public void setLastModifierId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.payment_receipt.last_modifier_id</code>.
     */
    public UUID getLastModifierId() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>public.payment_receipt.owner_id</code>.
     */
    public void setOwnerId(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.payment_receipt.owner_id</code>.
     */
    public UUID getOwnerId() {
        return (UUID) get(4);
    }

    /**
     * Setter for <code>public.payment_receipt.tag</code>.
     */
    public void setTag(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.payment_receipt.tag</code>.
     */
    public String getTag() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.payment_receipt.id</code>.
     */
    public void setId(UUID value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.payment_receipt.id</code>.
     */
    public UUID getId() {
        return (UUID) get(6);
    }

    /**
     * Setter for <code>public.payment_receipt.object</code>.
     */
    public void setObject(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.payment_receipt.object</code>.
     */
    public String getObject() {
        return (String) get(7);
    }

    /**
     * Setter for <code>public.payment_receipt.state</code>.
     */
    public void setState(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.payment_receipt.state</code>.
     */
    public String getState() {
        return (String) get(8);
    }

    /**
     * Setter for <code>public.payment_receipt.reference</code>.
     */
    public void setReference(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.payment_receipt.reference</code>.
     */
    public String getReference() {
        return (String) get(9);
    }

    /**
     * Setter for <code>public.payment_receipt.order_id</code>.
     */
    public void setOrderId(UUID value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.payment_receipt.order_id</code>.
     */
    public UUID getOrderId() {
        return (UUID) get(10);
    }

    /**
     * Setter for <code>public.payment_receipt.payment_date</code>.
     */
    public void setPaymentDate(LocalDate value) {
        set(11, value);
    }

    /**
     * Getter for <code>public.payment_receipt.payment_date</code>.
     */
    public LocalDate getPaymentDate() {
        return (LocalDate) get(11);
    }

    /**
     * Setter for <code>public.payment_receipt.description</code>.
     */
    public void setDescription(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>public.payment_receipt.description</code>.
     */
    public String getDescription() {
        return (String) get(12);
    }

    /**
     * Setter for <code>public.payment_receipt.request_id</code>.
     */
    public void setRequestId(UUID value) {
        set(13, value);
    }

    /**
     * Getter for <code>public.payment_receipt.request_id</code>.
     */
    public UUID getRequestId() {
        return (UUID) get(13);
    }

    /**
     * Setter for <code>public.payment_receipt.metadata</code>.
     */
    public void setMetadata(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>public.payment_receipt.metadata</code>.
     */
    public String getMetadata() {
        return (String) get(14);
    }

    /**
     * Setter for <code>public.payment_receipt.amount</code>.
     */
    public void setAmount(Double value) {
        set(15, value);
    }

    /**
     * Getter for <code>public.payment_receipt.amount</code>.
     */
    public Double getAmount() {
        return (Double) get(15);
    }

    /**
     * Setter for <code>public.payment_receipt.method_id</code>.
     */
    public void setMethodId(UUID value) {
        set(16, value);
    }

    /**
     * Getter for <code>public.payment_receipt.method_id</code>.
     */
    public UUID getMethodId() {
        return (UUID) get(16);
    }

    /**
     * Setter for <code>public.payment_receipt.remitter_id</code>.
     */
    public void setRemitterId(UUID value) {
        set(17, value);
    }

    /**
     * Getter for <code>public.payment_receipt.remitter_id</code>.
     */
    public UUID getRemitterId() {
        return (UUID) get(17);
    }

    /**
     * Setter for <code>public.payment_receipt.cashier_id</code>.
     */
    public void setCashierId(UUID value) {
        set(18, value);
    }

    /**
     * Getter for <code>public.payment_receipt.cashier_id</code>.
     */
    public UUID getCashierId() {
        return (UUID) get(18);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record19 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row19<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String, String, UUID, LocalDate, String, UUID, String, Double, UUID, UUID, UUID> fieldsRow() {
        return (Row19) super.fieldsRow();
    }

    @Override
    public Row19<LocalDateTime, UUID, LocalDateTime, UUID, UUID, String, UUID, String, String, String, UUID, LocalDate, String, UUID, String, Double, UUID, UUID, UUID> valuesRow() {
        return (Row19) super.valuesRow();
    }

    @Override
    public Field<LocalDateTime> field1() {
        return PaymentReceipt.PAYMENT_RECEIPT.CREATION_DATE;
    }

    @Override
    public Field<UUID> field2() {
        return PaymentReceipt.PAYMENT_RECEIPT.CREATOR_ID;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return PaymentReceipt.PAYMENT_RECEIPT.LAST_MODIFICATION_DATE;
    }

    @Override
    public Field<UUID> field4() {
        return PaymentReceipt.PAYMENT_RECEIPT.LAST_MODIFIER_ID;
    }

    @Override
    public Field<UUID> field5() {
        return PaymentReceipt.PAYMENT_RECEIPT.OWNER_ID;
    }

    @Override
    public Field<String> field6() {
        return PaymentReceipt.PAYMENT_RECEIPT.TAG;
    }

    @Override
    public Field<UUID> field7() {
        return PaymentReceipt.PAYMENT_RECEIPT.ID;
    }

    @Override
    public Field<String> field8() {
        return PaymentReceipt.PAYMENT_RECEIPT.OBJECT;
    }

    @Override
    public Field<String> field9() {
        return PaymentReceipt.PAYMENT_RECEIPT.STATE;
    }

    @Override
    public Field<String> field10() {
        return PaymentReceipt.PAYMENT_RECEIPT.REFERENCE;
    }

    @Override
    public Field<UUID> field11() {
        return PaymentReceipt.PAYMENT_RECEIPT.ORDER_ID;
    }

    @Override
    public Field<LocalDate> field12() {
        return PaymentReceipt.PAYMENT_RECEIPT.PAYMENT_DATE;
    }

    @Override
    public Field<String> field13() {
        return PaymentReceipt.PAYMENT_RECEIPT.DESCRIPTION;
    }

    @Override
    public Field<UUID> field14() {
        return PaymentReceipt.PAYMENT_RECEIPT.REQUEST_ID;
    }

    @Override
    public Field<String> field15() {
        return PaymentReceipt.PAYMENT_RECEIPT.METADATA;
    }

    @Override
    public Field<Double> field16() {
        return PaymentReceipt.PAYMENT_RECEIPT.AMOUNT;
    }

    @Override
    public Field<UUID> field17() {
        return PaymentReceipt.PAYMENT_RECEIPT.METHOD_ID;
    }

    @Override
    public Field<UUID> field18() {
        return PaymentReceipt.PAYMENT_RECEIPT.REMITTER_ID;
    }

    @Override
    public Field<UUID> field19() {
        return PaymentReceipt.PAYMENT_RECEIPT.CASHIER_ID;
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
        return getObject();
    }

    @Override
    public String component9() {
        return getState();
    }

    @Override
    public String component10() {
        return getReference();
    }

    @Override
    public UUID component11() {
        return getOrderId();
    }

    @Override
    public LocalDate component12() {
        return getPaymentDate();
    }

    @Override
    public String component13() {
        return getDescription();
    }

    @Override
    public UUID component14() {
        return getRequestId();
    }

    @Override
    public String component15() {
        return getMetadata();
    }

    @Override
    public Double component16() {
        return getAmount();
    }

    @Override
    public UUID component17() {
        return getMethodId();
    }

    @Override
    public UUID component18() {
        return getRemitterId();
    }

    @Override
    public UUID component19() {
        return getCashierId();
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
        return getObject();
    }

    @Override
    public String value9() {
        return getState();
    }

    @Override
    public String value10() {
        return getReference();
    }

    @Override
    public UUID value11() {
        return getOrderId();
    }

    @Override
    public LocalDate value12() {
        return getPaymentDate();
    }

    @Override
    public String value13() {
        return getDescription();
    }

    @Override
    public UUID value14() {
        return getRequestId();
    }

    @Override
    public String value15() {
        return getMetadata();
    }

    @Override
    public Double value16() {
        return getAmount();
    }

    @Override
    public UUID value17() {
        return getMethodId();
    }

    @Override
    public UUID value18() {
        return getRemitterId();
    }

    @Override
    public UUID value19() {
        return getCashierId();
    }

    @Override
    public PaymentReceiptRecord value1(LocalDateTime value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public PaymentReceiptRecord value2(UUID value) {
        setCreatorId(value);
        return this;
    }

    @Override
    public PaymentReceiptRecord value3(LocalDateTime value) {
        setLastModificationDate(value);
        return this;
    }

    @Override
    public PaymentReceiptRecord value4(UUID value) {
        setLastModifierId(value);
        return this;
    }

    @Override
    public PaymentReceiptRecord value5(UUID value) {
        setOwnerId(value);
        return this;
    }

    @Override
    public PaymentReceiptRecord value6(String value) {
        setTag(value);
        return this;
    }

    @Override
    public PaymentReceiptRecord value7(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public PaymentReceiptRecord value8(String value) {
        setObject(value);
        return this;
    }

    @Override
    public PaymentReceiptRecord value9(String value) {
        setState(value);
        return this;
    }

    @Override
    public PaymentReceiptRecord value10(String value) {
        setReference(value);
        return this;
    }

    @Override
    public PaymentReceiptRecord value11(UUID value) {
        setOrderId(value);
        return this;
    }

    @Override
    public PaymentReceiptRecord value12(LocalDate value) {
        setPaymentDate(value);
        return this;
    }

    @Override
    public PaymentReceiptRecord value13(String value) {
        setDescription(value);
        return this;
    }

    @Override
    public PaymentReceiptRecord value14(UUID value) {
        setRequestId(value);
        return this;
    }

    @Override
    public PaymentReceiptRecord value15(String value) {
        setMetadata(value);
        return this;
    }

    @Override
    public PaymentReceiptRecord value16(Double value) {
        setAmount(value);
        return this;
    }

    @Override
    public PaymentReceiptRecord value17(UUID value) {
        setMethodId(value);
        return this;
    }

    @Override
    public PaymentReceiptRecord value18(UUID value) {
        setRemitterId(value);
        return this;
    }

    @Override
    public PaymentReceiptRecord value19(UUID value) {
        setCashierId(value);
        return this;
    }

    @Override
    public PaymentReceiptRecord values(LocalDateTime value1, UUID value2, LocalDateTime value3, UUID value4, UUID value5, String value6, UUID value7, String value8, String value9, String value10, UUID value11, LocalDate value12, String value13, UUID value14, String value15, Double value16, UUID value17, UUID value18, UUID value19) {
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
        value12(value12);
        value13(value13);
        value14(value14);
        value15(value15);
        value16(value16);
        value17(value17);
        value18(value18);
        value19(value19);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PaymentReceiptRecord
     */
    public PaymentReceiptRecord() {
        super(PaymentReceipt.PAYMENT_RECEIPT);
    }

    /**
     * Create a detached, initialised PaymentReceiptRecord
     */
    public PaymentReceiptRecord(LocalDateTime creationDate, UUID creatorId, LocalDateTime lastModificationDate, UUID lastModifierId, UUID ownerId, String tag, UUID id, String object, String state, String reference, UUID orderId, LocalDate paymentDate, String description, UUID requestId, String metadata, Double amount, UUID methodId, UUID remitterId, UUID cashierId) {
        super(PaymentReceipt.PAYMENT_RECEIPT);

        setCreationDate(creationDate);
        setCreatorId(creatorId);
        setLastModificationDate(lastModificationDate);
        setLastModifierId(lastModifierId);
        setOwnerId(ownerId);
        setTag(tag);
        setId(id);
        setObject(object);
        setState(state);
        setReference(reference);
        setOrderId(orderId);
        setPaymentDate(paymentDate);
        setDescription(description);
        setRequestId(requestId);
        setMetadata(metadata);
        setAmount(amount);
        setMethodId(methodId);
        setRemitterId(remitterId);
        setCashierId(cashierId);
    }
}
