export class EditorFile {
    id: string;
    name: string;
    body: Array<number>;

    constructor(name: string, body: Array<number>, id?: string) {
        this.name = name;
        this.body = body;
        this.id = id;
    }
}
