const baseURL = "http://localhost:8080";
let currentPage = 0;
let keyword = "";

$(document).ready(function () {
  loadProducts();

  $("#productForm").on("submit", function (e) {
    e.preventDefault();
    const id = $("#productId").val();
    id ? updateProduct(id) : addProduct();
  });

  $("#searchInput").on("input", function () {
    keyword = $(this).val();
    currentPage = 0;
    loadProducts();
  });
});

function loadProducts() {
  $.ajax({
    url: `${baseURL}/product/all?page=${currentPage}&size=2&keyword=${keyword}`,
    method: "GET",
    xhrFields: { withCredentials: true },
    success: (res) => {
      const data = res.data;
      const rows = data.content.map(p => `
        <tr>
          <td><img src="${p.image}" /></td>
          <td>${p.name}</td>
          <td>${p.price}</td>
          <td>${p.stock}</td>
          <td>${p.description}</td>
          <td>
            <button onclick="editProduct(${p.id}, '${p.name}', ${p.price}, ${p.stock}, '${p.description}')">Edit</button>
            <button onclick="deleteProduct(${p.id})">Delete</button>
          </td>
        </tr>
      `).join("");
      $("#productTable").html(rows);
      $("#pageInfo").text(`Page ${data.number + 1} of ${data.totalPages}`);
    }
  });
}

function addProduct() {
  const formData = new FormData();
  const product = {
    name: $("#name").val(),
    price: Number($("#price").val()),
    stock: Number($("#stock").val()),
    description: $("#description").val()
  };

  formData.append("product", new Blob([JSON.stringify(product)], { type: "application/json" }));

  if (!$("#image")[0].files.length) {
    alert("Please select an image.");
    return;
  }

  formData.append("image", $("#image")[0].files[0]);

  $.ajax({
    url: `${baseURL}/product/add`,
    type: "POST",
    data: formData,
    contentType: false,
    processData: false,
    success: function (response) {
      if (response.success) {
        alert(response.message);
        closeModal();
        loadProducts();
      } else {
        alert("Failed to add product: " + response.message);
      }
    }
  });
}

function updateProduct(id) {
  const product = {
    name: $("#name").val(),
    price: Number($("#price").val()),
    stock: Number($("#stock").val()),
    description: $("#description").val()
  };

  $.ajax({
    url: `${baseURL}/product/update/${id}`,
    method: "PUT",
    contentType: "application/json",
    data: JSON.stringify(product),
    xhrFields: { withCredentials: true },
    success: function (response) {
      if (response.success) {
        alert(response.message);
        closeModal();
        loadProducts();
      } else {
        alert("Failed to update product: " + response.message);
      }
    }
  });
}

function deleteProduct(id) {
  if (!confirm("Are you sure?")) return;

  $.ajax({
    url: `${baseURL}/product/delete/${id}`,
    method: "DELETE",
    xhrFields: { withCredentials: true },
    success: function (response) {
      if (response.success) {
        alert(response.message);
        loadProducts();
      } else {
        alert("Failed to delete product: " + response.message);
      }
    }
  });
}

function editProduct(id, name, price, stock, description) {
  $("#productId").val(id);
  $("#name").val(name);
  $("#price").val(price);
  $("#stock").val(stock);
  $("#description").val(description);
  $("#image").val("");
  openModal(false);
}

function openModal(status) {
  $("#productModal").show();

  if (status) {
    $("#image").show();
  } else {
    $("#image").hide();
  }
}

function closeModal() {
  $("#productForm")[0].reset();
  $("#productId").val("");
  $("#productModal").hide();
}

function nextPage() {
  currentPage++;
  loadProducts();
}

function prevPage() {
  if (currentPage > 0) {
    currentPage--;
    loadProducts();
  }
}
