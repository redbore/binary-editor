import {Instance} from "./Instance";

export class Type {
  uuid: string;
  name: string;
  instances: Array<Instance>;

  constructor(
      uuid: string,
      name: string,
      instances: Array<Instance>
  ) {
  }
}
