import {Field} from "./Field";

export class Row {

    fields: Array<Field>;

    constructor(fields: Array<Field>) {
        this.fields = fields;
    }
}
