export class TableName {
  public uuid: string;
  public name: string;

  constructor(uuid: string, name: string) {
    this.uuid = uuid;
    this.name = name;
  }
}

export class Field {
  public uuid: string;
  public name: string;
  public value: any;

  constructor(uuid: string, name: string, value: any) {
    this.uuid = uuid;
    this.name = name;
    this.value = value;
  }
}

export class Row {
  public uuid: string;
  public fields: Array<Field>;

  constructor(uuid: string, fieldsViews: Array<Field>) {
    this.uuid = uuid;
    this.fields = fieldsViews;
  }
}

export class Table {
  public uuid: string = '';
  public columnsNames: Array<string>;
  public rows: Array<Row>;

  constructor(uuid: string, columnsNames: Array<string>, rows: Array<Row>) {
    this.uuid = uuid;
    this.columnsNames = columnsNames;
    this.rows = rows;
  }
}

export class Editor {
  public tablesNames: Array<TableName>;
  public selectedTable: Table;

  constructor(tablesNames: Array<TableName>, selectedTable: Table) {
    this.tablesNames = tablesNames;
    this.selectedTable = selectedTable;
  }
}

export class Paths {
  public xml: string;
  public binary: string;

  constructor(xml: string, binary: string) {
    this.xml = xml;
    this.binary = binary;
  }
}
