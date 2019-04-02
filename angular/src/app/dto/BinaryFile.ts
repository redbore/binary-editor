import {Type} from "./Type";

export class BinaryFile {
  public uuid: string;
  public types: Array<Type>;

  constructor(
      uuid: string,
      types: Array<Type>
  ) {
  }

  public findType(uuid: string): Type {
    return this.types.find(type => type.uuid == uuid);
  }
}
