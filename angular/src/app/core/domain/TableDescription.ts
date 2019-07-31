export class TableDescription {
    id: String;
    name: String;
    row_count: number;
    columns_names: Array<String>;

    constructor(id: String, name: String, row_count: number, columns_names: Array<String>) {
        this.id = id;
        this.name = name;
        this.row_count = row_count;
        this.columns_names = columns_names;
    }
}
