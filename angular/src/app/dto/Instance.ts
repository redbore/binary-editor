import {Field} from "./Field";

export class Instance {
  uuid: string;
  fields: Array<Field>;

  constructor(
      uuid: string,
      fields: Array<Field>
  ) {
  }

}
