import { ref, onMounted } from 'vue';
import GammaService from './GammaService.js';
import AlfaService from './AlfaService.js';
import BetaService from './BetaService.js';
import ErrorMessagesComponent from './ErrorMessagesComponent.js';

export default {
  template: `

    <error-messages-component ref="errorMessagesRef"></error-messages-component>

    <h2>Gamma</h2>

    <p>API URL: {{ apiUrl }}</p>

    <p><em><strong>Regla de negocio: </strong>Cuando se agrega un item, su entero se suma a los del alfa y beta seleccionados.</em></p>

    <button @click="getItems()">Traer items</button>
    <button @click="openIngresarItemDialog">Ingresar Item</button>

    <table>
      <thead>
        <tr>
          <th>id</th>
          <th>texto</th>
          <th>entero</th>
          <th>decimal</th>
          <th>alfa</th>
          <th>beta</th>
          <th>acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in items" :key="item.id">
          <td>{{ item.id }}</td>
          <td>{{ item.texto }}</td>
          <td>{{ item.entero }}</td>
          <td>{{ item.decimal }}</td>
          <td>{{ item.alfa.id }}: {{ item.alfa.texto }}</td>
          <td>{{ item.beta.id }}: {{ item.beta.texto }}</td>
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
        <fieldset>
					<legend>Alfa</legend>
					<label> <select v-model="currentItem.alfa.id" @change="selectAlfa">
							<option value="0">- Seleccionar -</option>
							<option :value="item.id" v-for="item of alfaItems">
                {{ item.id }}: {{ item.texto }}</option>
						</select>
					</label>
				</fieldset>
        <fieldset>
					<legend>Beta</legend>
					<label> <select v-model="currentItem.beta.id" @change="selectBeta">
							<option value="0">- Seleccionar -</option>
							<option :value="item.id" v-for="item of betaItems">
                {{ item.id }}: {{ item.texto }}</option>
						</select>
					</label>
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
        <fieldset>
					<legend>Alfa</legend>
					<label> <select v-model="currentItem.alfa.id" @change="selectAlfa">
							<option value="0">- Seleccionar -</option>
							<option :value="item.id" v-for="item of alfaItems">
                {{ item.id }}: {{ item.texto }}</option>
						</select>
					</label>
				</fieldset>
        <fieldset>
					<legend>Beta</legend>
					<label> <select v-model="currentItem.beta.id" @change="selectBeta">
							<option value="0">- Seleccionar -</option>
							<option :value="item.id" v-for="item of betaItems">
                {{ item.id }}: {{ item.texto }}</option>
						</select>
					</label>
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
    alfaApiUrl: {
      type: String,
      required: true,
    },
    betaApiUrl: {
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
      alfa: {
        id: 0,
      },
      beta: {
        id: 0,
      },
    });

    const alfaItems = ref([]);
    const betaItems = ref([]);

    const ingresarItemDialog = ref();
    const modificarItemDialog = ref();

    const gammaService = GammaService(props.apiUrl);
    const alfaService = AlfaService(props.alfaApiUrl);
    const betaService = BetaService(props.betaApiUrl);

    const getItems = async () => {
      try {
        items.value = await gammaService.getItems();
      } catch (error) {
        console.error('Failed to fetch items: ', error);
        errorMessagesRef.value.addErrorMessage('Failed to fetch items: ' + error.message);
      }
    };

    const getAlfaItems = async () => {
      try {
        alfaItems.value = await alfaService.getItems();
      } catch (error) {
        console.error('Failed to fetch items: ', error);
        errorMessagesRef.value.addErrorMessage('Failed to fetch items: ' + error.message);
      }
    };

    const getBetaItems = async () => {
      try {
        betaItems.value = await betaService.getItems();
      } catch (error) {
        console.error('Failed to fetch items: ', error);
        errorMessagesRef.value.addErrorMessage('Failed to fetch items: ' + error.message);
      }
    };

    const selectAlfa = () => {
			// nothing
		};

    const selectBeta = () => {
			// nothing
		};

    const getItem = async (id) => {
      try {
        currentItem.value = await gammaService.getItem(id);
      } catch (error) {
        console.error('Failed to fetch item:', error);
        errorMessagesRef.value.addErrorMessage('Failed to fetch item: ' + error.message);
      }
    };

    const createItem = async () => {
      try {
        await gammaService.createItem(currentItem.value);
        getItems();
        closeIngresarItemDialog();
      } catch (error) {
        console.error('Failed to create item:', error);
        errorMessagesRef.value.addErrorMessage('Failed to create item: ' + error.message);
      }
    };

    const updateItem = async () => {
      try {
        await gammaService.updateItem(currentItem.value.id, currentItem.value);
        getItems();
        closeModificarItemDialog();
      } catch (error) {
        console.error('Failed to update item:', error);
        errorMessagesRef.value.addErrorMessage('Failed to update item: ' + error.message);
      }
    };

    const openIngresarItemDialog = async () => {
      await getAlfaItems();
      await getBetaItems();
      ingresarItemDialog.value.showModal();
    };
    const closeIngresarItemDialog = () => ingresarItemDialog.value.close();

    const openModificarItemDialog = async (id) => {
      await getAlfaItems();
      await getBetaItems();
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
      alfaItems,
      betaItems,
      getItems,
      getItem,
      getAlfaItems,
      getBetaItems,
      selectAlfa,
      selectBeta,
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
