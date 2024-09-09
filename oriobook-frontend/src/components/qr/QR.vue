<template>
  <div>
    <!-- Button trigger modal -->
    <button
      type="button"
      class="btn btn-primary"
      data-bs-toggle="modal"
      data-bs-target="#qrCheckoutModal"
    >
      Launch demo modal
    </button>

    <!-- Modal -->
    <div
      class="modal fade"
      id="qrCheckoutModal"
      tabindex="-1"
      aria-labelledby="qrCheckoutLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <img :src="qr" alt="QR Code" />
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Close
            </button>
            <button type="button" class="btn btn-primary">Save changes</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted } from "vue";

export default {
  name: "QR",
  data() {
    // var qrModal = new bootstrap.Modal(
    //   document.getElementById("qrCheckoutModal")
    // );
    // qrModal.show();

    onMounted(() => {
      $(() => {
        $("#myModal").modal("show");
      });
    });

    return {
      qr: "https://img.vietqr.io/image/970415-113366668888-compact.png",
    };
  },
};
</script>

<style lang="scss" scoped></style>
