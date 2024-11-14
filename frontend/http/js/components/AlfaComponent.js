import { ref, onMounted } from 'vue';
import AlfaService from './AlfaService.js';
import ErrorMessagesComponent from './ErrorMessagesComponent.js';

export default {
  template: `

    <error-messages-component ref="errorMessagesRef"></error-messages-component>

    <h2>Alfa</h2>

    <p>API URL: {{ apiUrl }}</p>

    <button @click="getItems()">Traer items</button>
    <button @click="openIngresarItemDialog">Ingresar Item</button>

    <table>
      <thead>
        <tr>
          <th>id</th>
          <th>texto</th>
          <th>entero</th>
          <th>decimal</th>
          <th>acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in items" :key="item.id">
          <td>{{ item.id }}</td>
          <td>{{ item.texto }}</td>
          <td>{{ item.entero }}</td>
          <td>{{ item.decimal }}</td>
          <td>
            <button @click="openModificarItemDialog(item.id)">Modificar</button>
          </td>
        </tr>
      </tbody>
    </table>

    <dialog ref="ingresarItemDialog">
      <header>
        <h2>Ingresar Item</h2>
      </header>
      <form method="dialog">
        <fieldset>
          <label> Texto: <input type="text" v-model="currentItem.texto" /> </label> 
          <label> Entero: <input type="number" v-model="currentItem.entero" /> </label>
          <label> Decimal: <input type="number" step="0.1" v-model="currentItem.decimal" /> </label> 
        </fieldset>
        <footer>
          <button type="reset" @click="closeIngresarItemDialog">Cancelar</button>
          <button type="submit" @click.prevent="createItem">Guardar</button>
        </footer>
      </form>
    </dialog>

    <dialog ref="modificarItemDialog">
      <header>
        <h2>Modificar Item</h2>
      </header>
      <form method="dialog">
        <fieldset>
          <label> ID: {{ currentItem.id }} </label> 
          <label> Texto: <input type="text" v-model="currentItem.texto" /> </label> 
          <label> Entero: <input type="number" v-model="currentItem.entero" /> </label>
          <label> Decimal: <input type="number" step="0.1" v-model="currentItem.decimal" /> </label>
        </fieldset>
        <footer>
          <button type="reset" @click="closeModificarItemDialog">Cancelar</button>
          <button type="submit" @click.prevent="updateItem">Guardar</button>
        </footer>
      </form>
    </dialog>
  `,

  props: {
    apiUrl: {
      type: String,
      required: true,
    },
  },

  components: {
    ErrorMessagesComponent,
  },

  setup(props) {
    const errorMessagesRef = ref(null);

    const items = ref([]);
    const currentItem = ref({
      id: 0,
      texto: '',
      entero: 0,
      decimal: 0,
    });

    const ingresarItemDialog = ref();
    const modificarItemDialog = ref();

    const alfaService = AlfaService(props.apiUrl);

    const getItems = async () => {
      try {
        items.value = await alfaService.getItems();
      } catch (error) {
        console.error('Failed to fetch items: ', error);
        errorMessagesRef.value.addErrorMessage('Failed to fetch items: ' + error.message);
      }
    };

    const getItem = async (id) => {
      try {
        currentItem.value = await alfaService.getItem(id);
      } catch (error) {
        console.error('Failed to fetch item:', error);
        errorMessagesRef.value.addErrorMessage('Failed to fetch item: ' + error.message);
      }
    };

    const createItem = async () => {
      try {
        await alfaService.createItem(currentItem.value);
        getItems();
        closeIngresarItemDialog();
      } catch (error) {
        console.error('Failed to create item:', error);
        errorMessagesRef.value.addErrorMessage('Failed to create item: ' + error.message);
      }
    };

    const updateItem = async () => {
      try {
        await alfaService.updateItem(currentItem.value.id, currentItem.value);
        getItems();
        closeModificarItemDialog();
      } catch (error) {
        console.error('Failed to update item:', error);
        errorMessagesRef.value.addErrorMessage('Failed to update item: ' + error.message);
      }
    };

    const openIngresarItemDialog = () => {
      currentItem.value.id = 0;
      ingresarItemDialog.value.showModal();
    };

    const closeIngresarItemDialog = () => ingresarItemDialog.value.close();

    const openModificarItemDialog = async (id) => {
      await getItem(id);
      modificarItemDialog.value.showModal();
    };
    const closeModificarItemDialog = () => modificarItemDialog.value.close();

    onMounted(() => {
      getItems();
    });

    return {
      errorMessagesRef,
      items,
      currentItem,
      getItems,
      createItem,
      updateItem,
      openIngresarItemDialog,
      closeIngresarItemDialog,
      openModificarItemDialog,
      closeModificarItemDialog,
      ingresarItemDialog,
      modificarItemDialog,
    };
  },
};
