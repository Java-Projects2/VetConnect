alter table appointment
    add type enum ('checkup', 'vaccination', 'sterilization', 'surgery', 'emergency', 'follow_up', 'diagnosis', 'grooming', 'dental', 'consultation') default 'checkup' not null after status;

