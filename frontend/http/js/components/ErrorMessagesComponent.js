import { ref, watch, nextTick } from 'vue';

const ErrorMessagesComponent = {
    template: `
      <div class="flash danger error-messages" ref="errorContainer" v-if="errorMessages.length > 0">
        <div class="error-message-header">
          <a href="#" @click.prevent="clearErrorMessages">[clear]</a>
        </div>
        <ul>
          <li v-for="(errorMessage, index) in errorMessages" :key="index">
            <a href="#" @click.prevent="removeErrorMessage(index)">[x]</a>
            {{ errorMessage }}
          </li>
        </ul>
      </div>
    `,
    setup() {
      const errorMessages = ref([]);
  
      const addErrorMessage = (message) => {console.log('AAA')
        errorMessages.value.push(message);
      };
  
      const removeErrorMessage = (index) => {
        errorMessages.value.splice(index, 1);
      };
  
      const clearErrorMessages = () => {
        errorMessages.value = [];
      };

      watch(
        errorMessages,
        () => {
            // Al cambiar errorMessages, desplazar al final
            nextTick(() => {
                if (errorContainer.value) {
                    errorContainer.value.scrollTop = errorContainer.value.scrollHeight;
                }
            });
        },
        { deep: true }
    );
  
      return {
        errorMessages,
        addErrorMessage,
        removeErrorMessage,
        clearErrorMessages,
      };
    },
  };
  
  export default ErrorMessagesComponent;  