namespace my.sample;

using {
    sap,
    managed,
    cuid
} from '@sap/cds/common';

@fiori.draft.enabled
entity Books : cuid, managed {
    title        : localized String(111);
    descr        : localized String(1111);
    stock        : Integer;
    price        : Decimal(9, 2);
}
