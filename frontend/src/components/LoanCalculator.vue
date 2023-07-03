<template>
  <form @submit.prevent="handleSubmit">
    <label>Personal code: </label>
    <input type="number" v-model="userId">

    <label>Amount: </label>
    <input type="number" required v-model="amount">

    <label>Period: </label>
    <input type="number" required v-model="period">

    <div class="submit">
      <button>Calculate</button>
    </div>


    <div v-if="showPopup" class="popup">
      <div class="popup-content">
        <h2>Result:</h2>
        <pre>Loan status: {{ responseLoanStatus }}</pre>
        <div v-if="responseLoanStatus=='APPROVED'">
          <pre>Amount: {{ responseAmount }}</pre>
          <pre>Period: {{ responsePeriod }}</pre>
        </div>
        <div v-else>
          <pre>Reason: {{ responseReason }}</pre>
        </div>
        <button @click="closePopup">Close</button>
      </div>
    </div>

  </form>

</template>

<script>
export default {
    data(){
        return {
            userId: '49002010965',
            amount: '3000',
            period: '12',

            showPopup: false,
            responseData: null,

            responseLoanStatus: '',
            responseAmount: '',
            responsePeriod: '',
            responseReason: ''
        }
    },
    methods: {
      handleSubmit() {
        const params = new URLSearchParams({
          userId: this.userId,
          amount: this.amount,
          period: this.period
        });
        fetch('http://localhost:8080/api/v1/loan/calculate?'+params)
            .then(response => response.json())
            .then(data => {
               this.responseLoanStatus = data.status;
              if(this.responseLoanStatus == 'APPROVED') {
                 this.responseAmount = data.amount;
                 this.responsePeriod = data.period;
              }
              else if(this.responseLoanStatus == 'REJECTED') {
                this.responseReason = data.reason;
              }
              else {
                this.responseLoanStatus = 'REJECTED';
                this.responseReason = data.message;
              }

              this.showPopup = true;
            })
            .catch(error => {
              console.error('Error fetching data:', error);
            });
      },
      closePopup() {
        this.showPopup = false;
        this.responseData = null;
      },
    }

}
</script>

<style>
  form {
    max-width: 420px;
    margin: 30px auto;
    background: white;
    text-align: left;
    padding: 40px;
    border-radius: 10px;
  }
  label {
    color: #aaa;
    display: inline-block;
    margin: 25px 0 15px;
    font-size: 0.6em;
    text-transform: uppercase;
    letter-spacing: 1px;
    font-weight: bold;
  }
  input, select {
    display: block;
    padding: 10px 6px;
    width: 100%;
    box-sizing: border-box;
    border: none;
    border-bottom: 1px solid #ddd;
    color: #555;
  }
  input[type="checkbox"] {
    display: inline-block;
    width: 16px;
    margin: 0 10px 0 0;
    position: relative;
    top: 2px;
  }
  button {
    background: #0b6dff;
    border: 0;
    padding: 10px 20px;
    margin-top: 20px;
    color: white;
    border-radius: 20px;
  }
  .submit {
    text-align: center;
  }
  .error {
    color: #ff0062;
    margin-top: 10px;
    font-size: 0.8em;
    font-weight: bold;
  }
  .popup {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .popup-content {
    background-color: #fff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
    max-width: 400px;
    width: 100%;
  }

</style>
