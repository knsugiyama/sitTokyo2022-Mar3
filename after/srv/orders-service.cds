using {my.sample as my} from '../db';

service OrdersService {

    entity Orders as select from my.Orders
}
