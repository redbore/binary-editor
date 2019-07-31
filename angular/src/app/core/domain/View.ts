import {EditorFile} from "./EditorFile";
import {TableDescription} from "./TableDescription";

export class View {
    specification: EditorFile;
    binary_file: EditorFile;
    tables_descriptions: Array<TableDescription>;

    constructor(specification: EditorFile, binary_file: EditorFile, tables_descriptions: Array<TableDescription>) {
        this.specification = specification;
        this.binary_file = binary_file;
        this.tables_descriptions = tables_descriptions;
    }
}
