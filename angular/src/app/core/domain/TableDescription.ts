export class TableDescription {
    id: string;
    name: string;
    row_count: number;
    columns_names: Array<string>;

    constructor(id: string, name: string, row_count: number, columns_names: Array<string>) {
        this.id = id;
        this.name = name;
        this.row_count = row_count;
        this.columns_names = columns_names;
    }
}
