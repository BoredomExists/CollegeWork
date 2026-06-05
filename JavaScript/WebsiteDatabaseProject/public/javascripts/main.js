$(document).ready(() => {
    $("#billing").click(() => {
        window.location.href = '/'
    })

    $("#medical").click(() => {
        window.location.href = '/medical';
    })

    $("#patients").click(() => {
        window.location.href = '/patients'
    })


    $("#billingForm").submit((e) => {
        e.preventDefault();
        const data = $('#billingForm').serialize();

        $.ajax({
            type: 'POST',
            url: '/insert',
            data: data,
            success: (response) => {
                alert('New Record added to Billing');
                window.location.reload();
            },
            error: (err) => {
                console.error(err);
            }
        });
    });


    $("#medicalForm").submit((e) => {
        e.preventDefault();
        const data = $('#medicalForm').serialize();

        $.ajax({
            type: 'POST',
            url: '/medical',
            data: data,
            success: (response) => {
                alert('New Record added to Medical');
                window.location.reload();
            },
            error: (err) => {
                console.error(err);
            }
        });
    });

    $("#patientsForm").submit((e) => {
        e.preventDefault();
        const data = $('#patientsForm').serialize();

        $.ajax({
            type: 'POST',
            url: '/patients',
            data: data,
            success: (response) => {
                alert('New Record added to Patients');
                window.location.reload();
            },
            error: (err) => {
                console.error(err);
            }
        });
    });

});