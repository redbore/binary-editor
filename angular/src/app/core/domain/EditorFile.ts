export class EditorFile {
    id: String;
    name: String;
    body: Array<Number>;

    constructor(id: String, name: String, body: Array<Number>) {
        this.id = id;
        this.name = name;
        this.body = body;
    }
}
