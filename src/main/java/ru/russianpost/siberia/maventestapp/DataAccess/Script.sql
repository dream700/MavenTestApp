/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Andrey.Isakov
 * Created: Dec 26, 2017
 */

create table app.historyrecord (id  serial not null, ComplexItemName varchar(255), CountryOperID int4, DestinationAddress_Index varchar(6), destinationaddress_description varchar(255), lastOperDate date, MailDirectID int4, MailDirectNameRU varchar(255), Mass varchar(10), OperAttrID int4, OperAttrName varchar(255), OperDate date, OperTypeID int4, OperTypeName varchar(255), OperationAddress_Description varchar(255), OperationAddress_Index varchar(6), OperatonDelta int4, Rcpn varchar(255), Sndr varchar(255), barcode varchar(16), primary key (id))
create table app.ticket (barcode varchar(16) not null, DateFetch date, primary key (barcode))
alter table app.historyrecord add constraint FKbqefn6lmemfiw0t5nvf5eic91 foreign key (barcode) references app.ticket
