namespace my.sample;

using {
    User,
    managed,
    cuid
} from '@sap/cds/common';

using my.sample.Books from './books';

entity Orders : cuid, managed {
    OrderNo  : String @mandatory; //> readable key
    Items    : Composition of many OrderItems
                   on Items.parent = $self;
    total    : Decimal(9, 2)@readonly;
}

entity OrderItems : cuid {
    parent    : Association to Orders;
    book      : Association to Books;
    quantity    : Integer;
    amount : Decimal(9, 2);
}
