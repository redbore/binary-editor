export class EditorFile {
    id: String;
    name: String;
    body: Array<Number>;

    constructor(name: String, body: Array<Number>, id?: String) {
        this.name = name;
        this.body = body;
        this.id = id;
    }
}
